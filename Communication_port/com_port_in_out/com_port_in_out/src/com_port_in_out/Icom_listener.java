/*
 * Icom_listener.java
 *
 * Created on 24 ќкт€брь 2007 г., 19:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com_port_in_out;

/**
 * »нтерфейс, который €вл€етс€ слушателем данных из порта
 * @author root
 */
public interface Icom_listener {
    public void data_from_com(byte[] data);
}
