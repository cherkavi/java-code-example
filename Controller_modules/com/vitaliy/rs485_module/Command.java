package com.vitaliy.rs485_module;

public abstract class Command {
	abstract public String get_command();
	abstract public boolean response_is_valid(String response);
	abstract public void clear();
	abstract public String get_name();
}
