package com.logit_attendance.logit.utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Sibling<T>{
	
	private LinkedHashMap<T, LinkedHashMap<String, T>> siblings;
	private final ArrayList<T> elements = new ArrayList<>();
	private boolean loop = true;

	@SafeVarargs
	public Sibling(T... elements) {
		for(T element : elements) {this.elements.add(element);}
		addElements(this.elements);
	}

	private void addElements(ArrayList<T> elements) {
		siblings = new LinkedHashMap<>();{
			siblings.clear();
			for(int num = 0; num < elements.size(); num++) {
				T prev = loop ? num == 0 ? elements.get(elements.size()-1) : elements.get(num-1) :
						(num == 0 ? elements.get(0) : elements.get(num-1));

				T element = elements.get(num);

				T next = loop ? num == elements.size()-1 ? elements.get(0) : elements.get(num+1) :
						num == elements.size()-1 ? elements.get(num) : elements.get(num+1);

				LinkedHashMap<String, T> sibling = new LinkedHashMap<String, T>();{
					sibling.put("prev", prev);
					sibling.put("next", next);
				}
				siblings.put(element, sibling);
			}
		}
	}

	public void add(Integer indexPosition, T element) {
		elements.add(indexPosition, element);
		addElements(elements);
	}

	public void remove(Integer indexPosition) {
		elements.remove(indexPosition);
		addElements(elements);
	}

	public LinkedHashMap<String, T> getSibling(T element) {
		return siblings.get(element);
	}

	public T previous(T element) {
		element = (siblings.containsKey(element) ? siblings.get(element).get("prev") : element);
		return element;
	}

	public T next(T element) {
		element = (siblings.containsKey(element) ? siblings.get(element).get("next") : element);
		return element;
	}

	public LinkedHashMap<T, LinkedHashMap<String, T>> getElements() {
		return siblings;
	}

	@SuppressWarnings("unchecked")
	public T getElement(int index) {
		return (T) siblings.keySet().toArray()[index];
	}

	public Sibling<T> loopSibling(boolean bool) {
		loop = bool;
		addElements(elements);
		return this;
	}
	
}