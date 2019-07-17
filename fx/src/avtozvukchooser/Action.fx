package avtozvukchooser;

import javafx.scene.Scene;

/**
 * @author cherkashinv
 */
public class Action{
	/** текущая сцена */
	public var currentScene:Scene;
	/** зарегистрированные имена сцен */
	var names:String[];
	/** зарегистрированные сцены , которые соответствуют именам */
	var scenes:Scene[];

	/** добавить сцену в список */
	public function addScene(sceneName:String, sceneValue:Scene){
	    insert sceneName into names;
	    insert sceneValue into scenes;
            if((sizeof scenes)==1){
                this.currentScene=sceneValue;
            }
	}

	/** удалить текущую сцену из списка */
	public function removeScene(sceneName:String){
	    for(index in [0..((sizeof names)-1)]){
	        if(sceneName.equals(names[index])){
	            delete names[index];
	            break;
	        }
	    }
	}

	/** установить текщую сцену по имени */
	public function setCurrent(sceneName){
	    println("names: {names}");
            for(index in [0..((sizeof names)-1)]){
                println(" Names[{index}]={names[index]}   sceneName:{sceneName}");
	        if(names[index].equals(sceneName)){
	           this.currentScene=scenes[index];
                   break;
	        }
	    }
	}
}
