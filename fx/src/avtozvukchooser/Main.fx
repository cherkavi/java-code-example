package avtozvukchooser;
/**
 * @author cherkashinv
 */
import javafx.scene.Scene;
import javafx.stage.Stage;
import avtozvukchooser.window.*;


/** объект, который содержит необходимые данные для перехода между окнами */
var action=Action{};
/** текущая сцена для данного объекта */
var currentScene=bind action.currentScene;

/** добавить все возможные сцены для объекта */
var firstScene=Login{action:action};

action.addScene("Login", Scene{content: [firstScene]});
action.addScene("Workplace", Scene{content: [Workplace{mainAction:action}]});

Stage{
	title:"Сборка";
	width : 300
	height : 300
	visible : true
	resizable : false
	scene: bind currentScene;

}