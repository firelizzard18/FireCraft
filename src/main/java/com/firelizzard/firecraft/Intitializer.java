package com.firelizzard.firecraft;

import static org.reflections.ReflectionUtils.getAllMethods;
import static org.reflections.ReflectionUtils.withAnnotation;
import static org.reflections.ReflectionUtils.withModifier;
import static org.reflections.ReflectionUtils.withParametersCount;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reflections.Reflections;

import com.firelizzard.firecraft.api.Initialization;
import com.google.common.base.Predicate;

class Intitializer {
	private static final Predicate<AnnotatedElement> preinit = withAnnotation(Initialization.Pre.class);
	private static final Predicate<AnnotatedElement> duringinit = withAnnotation(Initialization.During.class);
	private static final Predicate<AnnotatedElement> postinit = withAnnotation(Initialization.Post.class);
	
//	private static final Predicate<Method> retvoid = withReturnType(Void.class);
	private static final Predicate<Member> noparams = withParametersCount(0);
	private static final Predicate<Member> staticmod = withModifier(Modifier.STATIC);
	private static final Predicate<Member> publicmod = withModifier(Modifier.PUBLIC);
	
	
	interface Call {
		void execute();
	}
	
	static class Node {
		public Call call;
		public Set<Node> prerequisites;
		
		public Node(Call call) {
			this.call = call;
		}
	}
	
	static class ClassNode extends Node {
		public Class<?> clazz;
		
		public ClassNode(Class<?> clazz) {
			super(() -> {
				try {
					Class.forName(clazz.getName(), true, clazz.getClassLoader());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			});
			this.clazz = clazz;
		}
	}
	
	static class MethodNode extends Node {
		public Method method;
		
		public MethodNode(Method method) {
			super(() -> {
				try {
					method.invoke(null);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			});
			this.method = method;
		}
	}
	
	static void call(Stream<? extends Node> nodes) {
		List<Node> remaining = nodes.collect(Collectors.toList());
		List<Node> calls = new ArrayList<>();
		
		while (!remaining.isEmpty()) {
			boolean didAdd = false;
			for (int i = remaining.size() - 1; i >= 0; i--) {
				if (remaining.get(i).prerequisites.stream().anyMatch(x -> !calls.contains(x)))
					continue;
				
				calls.add(remaining.remove(i));
				didAdd = true;
			}
			if (!didAdd)
				throw new RuntimeException("Unsatisfiable prerequisite loop");
		}
		
		for (Node call : calls)
			call.call.execute();
	}
	
	private ArrayList<Class<?>> classes = new ArrayList<>();
	
	public void callClassInitializers() {
		Reflections reflections = new Reflections(getClass().getPackage().getName());

		Map<Class<?>, ClassNode> nodes = new HashMap<>();
		for (Class<?> clazz : reflections.getTypesAnnotatedWith(Initialization.class)) {
			classes.add(clazz);
			nodes.put(clazz, new ClassNode(clazz));
		}

		ArrayList<ClassNode> extras = new ArrayList<>();
		for (ClassNode node : nodes.values()) {
			Initialization anno = node.clazz.getAnnotation(Initialization.class);
			node.prerequisites = Stream.of(anno.after()).map(x -> {
				if (nodes.containsKey(x))
					return nodes.get(x);
				
				// handle after annotations for non-initializer classes
				ClassNode _node = new ClassNode(x);
				_node.prerequisites = new HashSet<>();
				extras.add(_node);
				return _node;
			}).collect(Collectors.toSet());
		}
		
		for (ClassNode node : extras)
			nodes.put(node.clazz, node);
		
		call(nodes.values().stream());
	}

	@SuppressWarnings("unchecked")
	public void callPreInitializers() {
		Map<Class<?>, List<MethodNode>> nodes = new HashMap<>();

		for (Class<?> clazz : classes) {
			List<MethodNode> classNodes = new ArrayList<>();
			for (Method method : getAllMethods(clazz, preinit, noparams, staticmod, publicmod))
				classNodes.add(new MethodNode(method));
			if (classNodes.isEmpty())
				continue;
			nodes.put(clazz, classNodes);
		}
		
		nodes.values().stream().flatMap(x -> x.stream()).forEach(node -> {
			Initialization.Pre anno = node.method.getAnnotation(Initialization.Pre.class);
			node.prerequisites = Stream.of(anno.after()).flatMap(x -> nodes.get(x).stream()).collect(Collectors.toSet());
		});
		
		call(nodes.values().stream().flatMap(x -> x.stream()));
	}

	@SuppressWarnings("unchecked")
	public void callInitializers() {
		Map<Class<?>, List<MethodNode>> nodes = new HashMap<>();

		for (Class<?> clazz : classes) {
			List<MethodNode> classNodes = new ArrayList<>();
			for (Method method : getAllMethods(clazz, duringinit, noparams, staticmod, publicmod))
				classNodes.add(new MethodNode(method));
			if (classNodes.isEmpty())
				continue;
			nodes.put(clazz, classNodes);
		}
		
		nodes.values().stream().flatMap(x -> x.stream()).forEach(node -> {
			Initialization.During anno = node.method.getAnnotation(Initialization.During.class);
			node.prerequisites = Stream.of(anno.after()).flatMap(x -> nodes.get(x).stream()).collect(Collectors.toSet());
		});
		
		call(nodes.values().stream().flatMap(x -> x.stream()));
	}

	@SuppressWarnings("unchecked")
	public void callPostInitializers() {
		Map<Class<?>, List<MethodNode>> nodes = new HashMap<>();

		for (Class<?> clazz : classes) {
			List<MethodNode> classNodes = new ArrayList<>();
			for (Method method : getAllMethods(clazz, postinit, noparams, staticmod, publicmod))
				classNodes.add(new MethodNode(method));
			if (classNodes.isEmpty())
				continue;
			nodes.put(clazz, classNodes);
		}
		
		nodes.values().stream().flatMap(x -> x.stream()).forEach(node -> {
			Initialization.Post anno = node.method.getAnnotation(Initialization.Post.class);
			node.prerequisites = Stream.of(anno.after()).flatMap(x -> nodes.get(x).stream()).collect(Collectors.toSet());
		});
		
		call(nodes.values().stream().flatMap(x -> x.stream()));
	}
}
