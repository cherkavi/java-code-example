package com.cherkashyn.vitaliy.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class CaseInsensitiveMapCustom2<K> implements Map<String, K>{
	Map<SurrogateKey, K> delegate=new HashMap<SurrogateKey, K>();

	public int size() {
		return delegate.size();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public boolean containsKey(Object key) {
		return delegate.containsKey(new SurrogateKey(key));
	}

	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	public K get(Object key) {
		return delegate.get(new SurrogateKey(key));
	}

	public K put(String key, K value) {
		return delegate.put(new SurrogateKey(key), value);
	}

	public K remove(Object key) {
		return delegate.remove(new SurrogateKey(key));
	}

	public void putAll(Map<? extends String, ? extends K> m) {
		if(m!=null)
		for(Entry<? extends String, ? extends K> eachValue:m.entrySet()){
			delegate.put(new SurrogateKey(eachValue.getKey()), eachValue.getValue());
		}
	}

	public void clear() {
		delegate.clear();
	}

	public Set<String> keySet() {
		Set<String> returnValue=new HashSet<String>(delegate.size());
		for(SurrogateKey eachKey: delegate.keySet()){
			returnValue.add(eachKey.getValue());
		}
		return returnValue;
	}

	public Collection<K> values() {
		return delegate.values();
	}

	public Set<Entry<String, K>> entrySet() {
		Set<Entry<String,K>> returnValue=new HashSet<Entry<String, K>>(delegate.size());
		for(Entry<SurrogateKey, K> eachValue: delegate.entrySet()){
			returnValue.add(new AttributeEntry(eachValue.getKey().getValue(), eachValue.getValue()));
		}
		return returnValue;
	}

	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	
}

class SurrogateKey{
	private String value;
	private String surrogateValue;

	public SurrogateKey(Object value) {
		super();
		if(value!=null){
			this.value = value.toString();
			this.surrogateValue=transformToSurrogate(this.value);
		}
	}

	
	private String transformToSurrogate(Object value){
		if(value==null){
			return null;
		}
		return StringUtils.lowerCase(value.toString());
	}

	public String getValue() {
		return value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((surrogateValue == null) ? 0 : surrogateValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SurrogateKey other = (SurrogateKey) obj;
		if (surrogateValue == null) {
			if (other.surrogateValue != null)
				return false;
		} else if (!surrogateValue.equals(other.surrogateValue))
			return false;
		return true;
	}
	
}


class AttributeEntry implements Map.Entry {

    private String key;
    private Object value;

    AttributeEntry(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public Object setValue(Object newValue) {
        throw new UnsupportedOperationException();
    }


    public String toString() {
        return key.toString()+"="+value.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeEntry other = (AttributeEntry) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
    
    
}

