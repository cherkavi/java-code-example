/*
 * ComListener.java
 *
 * Created on 9 ќкт€брь 2007 г., 8:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.vitaliy.rs485_module;

/**
 * »нтерфейс, в который передаетс€ информаци€, считанна€ с COM порта
 */
public interface ComListener {
    public void data_from_port(String s);
}
