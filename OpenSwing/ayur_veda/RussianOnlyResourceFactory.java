package ayur_veda;

import java.util.Map;
import java.util.Properties;

import org.openswing.swing.internationalization.java.Resources;
import org.openswing.swing.internationalization.java.ResourcesFactory;

public class RussianOnlyResourceFactory extends ResourcesFactory {

	  /** internationalization settings */
	  private Resources resources = null;
	  public RussianOnlyResourceFactory(Map<? extends Object, ? extends Object> additionalDictionary){
		  init(additionalDictionary);
	  }
	  
	  
	  public RussianOnlyResourceFactory(){
		  init(null);
	  }
	  
	  private void init(Map<? extends Object, ? extends Object> additionalDictionary){
		  Properties dictionary = new Properties();
		  if(additionalDictionary!=null){
			  dictionary.putAll(additionalDictionary);
		  }
		    // grid...
		    dictionary.setProperty("of","из");
		    dictionary.setProperty("page","Страница");
		    dictionary.setProperty("Remove Filter","Удалить фильтр");
		    dictionary.setProperty("This column is not sorteable","Колонка не сортируемая");
		    dictionary.setProperty("Sorting not allowed","Сортировка не поддерживается");
		    dictionary.setProperty("Maximum number of sorted columns","Максимальное кол-во сортируемых колонок");
		    dictionary.setProperty("Sorting not applicable","Сортировка не допустима");
		    dictionary.setProperty("Selected Row","Выделенная строк");
		    dictionary.setProperty("Selected Rows","Выделенные строки");
		    dictionary.setProperty("Cancel changes and reload data?","Отменить изменения и перегрузить данные?");
		    dictionary.setProperty("Attention","Внимание");
		    dictionary.setProperty("Loading data...","Загрузка данных...");
		    dictionary.setProperty("Error while loading data","Ошибка во время загрузки данных");
		    dictionary.setProperty("Loading Data Error","Ошибка загрузки данных");
		    dictionary.setProperty("Delete Rows?","Удалить строки ?");
		    dictionary.setProperty("Delete Confirmation","Подтверждение удаления");
		    dictionary.setProperty("Error while deleting rows.","Ошибка во время удаления строк.");
		    dictionary.setProperty("Deleting Error","Ошибка удаления");
		    dictionary.setProperty("Error while saving","Ошибка во время сохранения");
		    dictionary.setProperty("Saving Error","Ошибка сохранения");
		    dictionary.setProperty("A mandatory column is empty.","A mandatory column is empty");
		    dictionary.setProperty("Value not valid","Value not valid");
		    dictionary.setProperty("sorting conditions","Sorting conditions");
		    dictionary.setProperty("filtering conditions","Filtering conditions");
		    dictionary.setProperty("filtering and sorting settings","Filtering and sorting settings");
		    dictionary.setProperty("Filtering/Sorting data (CTRL+F)","Filtering/Sorting data (CTRL+F)");
		    dictionary.setProperty("upload file","Upload File");
		    dictionary.setProperty("download file","Download File");

		    // export...
		    dictionary.setProperty("grid export","Grid Export");
		    dictionary.setProperty("export","Export");
		    dictionary.setProperty("exportmnemonic","X");
		    dictionary.setProperty("column","Column");
		    dictionary.setProperty("sel.","Sel.");
		    dictionary.setProperty("you must select at least one column","You must select at least one column");
		    dictionary.setProperty("columns to export","Columns to export");
		    dictionary.setProperty("export type","Export format");

		    // import...
		    dictionary.setProperty("grid import","Grid Import");
		    dictionary.setProperty("file to import","File to import");
		    dictionary.setProperty("import","Import");
		    dictionary.setProperty("importmnemonic","M");
		    dictionary.setProperty("columns to import","Columns to import");
		    dictionary.setProperty("import type","Import format");
		    dictionary.setProperty("error while importing data","Error while importing data");
		    dictionary.setProperty("import completed","Import completed.");

		    // quick filter...
		    dictionary.setProperty("To value","To value");
		    dictionary.setProperty("Filter by","Filter by");
		    dictionary.setProperty("From value","From value");
		    dictionary.setProperty("equals","equals");
		    dictionary.setProperty("contains","contains");
		    dictionary.setProperty("starts with","starts with");
		    dictionary.setProperty("ends with","ends with");

		    // select/deselect all
		    dictionary.setProperty("select all","Выделить все");
		    dictionary.setProperty("deselect all","Отменить выделение всего");

		    // copy/cut/paste
		    dictionary.setProperty("copy","Copy");
		    dictionary.setProperty("copymnemonic","C");
		    dictionary.setProperty("cut","Cut");
		    dictionary.setProperty("cutmnemonic","U");
		    dictionary.setProperty("paste","Paste");
		    dictionary.setProperty("pastemnemonic","P");

		    // lookup...
		    dictionary.setProperty("Code is not correct.","Code is not correct.");
		    dictionary.setProperty("Code Validation","Code Validation");
		    dictionary.setProperty("Code Selection","Code Selection");

		    // form...
		    dictionary.setProperty("Confirm deliting data?","Confirm deliting data?");
		    dictionary.setProperty("Error while saving: incorrect data.","Error while saving: incorrect data.");
		    dictionary.setProperty("Error while validating data:","Error while validating data:");
		    dictionary.setProperty("Validation Error","Validation Error");
		    dictionary.setProperty("Error on deleting:","Error on deleting:");
		    dictionary.setProperty("Error on Loading","Error on Loading");
		    dictionary.setProperty("Error while loading data:","Error while loading data:");
		    dictionary.setProperty("Error on setting value to the input control having the attribute name","Error on setting value to the input control having the attribute name");

		    // toolbar buttons...
		    dictionary.setProperty("Delete record (CTRL+D)","Delete record (CTRL+D)");
		    dictionary.setProperty("Edit record (CTRL+E)","Edit record (CTRL+E)");
		    dictionary.setProperty("New record (CTRL+I)","New record (CTRL+I)");
		    dictionary.setProperty("Reload record/Cancel current operation (CTRL+Z)","Reload record/Cancel current operation (CTRL+Z)");
		    dictionary.setProperty("Save record (CTRL+S)","Save record (CTRL+S)");
		    dictionary.setProperty("Copy record (CTRL+C)","Copy record (CTRL+C)");
		    dictionary.setProperty("Export record (CTRL+X)","Export records (CTRL+X)");
		    dictionary.setProperty("Import records (CTRL+M)","Import records (CTRL+M)");
		    dictionary.setProperty("Load the first block of records","Load the first block of records");
		    dictionary.setProperty("Select the previous row in grid","Select the previous row in grid");
		    dictionary.setProperty("Select the next row in grid","Select the next row in grid");
		    dictionary.setProperty("Load the previous block of records","Load the previous block of records");
		    dictionary.setProperty("Load the next block of records","Load the next block of records");
		    dictionary.setProperty("Load the last block of records","Load the last block of records");

		    dictionary.setProperty("Insert","Insert");
		    dictionary.setProperty("Edit","Edit");
		    dictionary.setProperty("Copy","Copy");
		    dictionary.setProperty("Delete","Delete");
		    dictionary.setProperty("Save","Save");
		    dictionary.setProperty("Reload","Reload");
		    dictionary.setProperty("Export","Export");
		    dictionary.setProperty("Filter","Filter");

		    // MDI Frame...
		    dictionary.setProperty("file","Файл");
		    dictionary.setProperty("exit","Выход");
		    dictionary.setProperty("filemnemonic","F");
		    dictionary.setProperty("exitmnemonic","E");
		    dictionary.setProperty("change user","Смена пользователя");
		    dictionary.setProperty("changeusermnemonic","U");
		    dictionary.setProperty("changelanguagemnemonic","L");
		    dictionary.setProperty("help","Помощь");
		    dictionary.setProperty("about","О программе");
		    dictionary.setProperty("helpmnemonic","H");
		    dictionary.setProperty("aboutmnemonic","A");
		    dictionary.setProperty("are you sure to quit application?","Вы действительно хотите закрыть приложение?");
		    dictionary.setProperty("quit application","Выход из прилолжения");
		    dictionary.setProperty("forcegcmnemonic","F");
		    dictionary.setProperty("Force GC","Force GC");
		    dictionary.setProperty("Java Heap","Java Heap");
		    dictionary.setProperty("used","used");
		    dictionary.setProperty("allocated","allocated");
		    dictionary.setProperty("change language","Смена языка");
		    dictionary.setProperty("changemnemonic","L");
		    dictionary.setProperty("cancelmnemonic","C");
		    dictionary.setProperty("cancel","Отмена");
		    dictionary.setProperty("yes","Да");
		    dictionary.setProperty("no","Нет");
		    dictionary.setProperty("Functions","Функции");
		    dictionary.setProperty("Error while executing function","Ошибка во время выполнения функции");
		    dictionary.setProperty("Error","Ошибка");
		    dictionary.setProperty("infoPanel","Информация");
		    dictionary.setProperty("imageButton","О программе");
		    dictionary.setProperty("Window","Окно");
		    dictionary.setProperty("windowmnemonic","W");
		    dictionary.setProperty("Close All","Close All");
		    dictionary.setProperty("closeallmnemonic","A");
		    dictionary.setProperty("Press ENTER to find function","Нажмите ENTER чтобы найти функцию");
		    dictionary.setProperty("Find Function","Поиск функции");
		    dictionary.setProperty("Operation in progress...","Операция в процессе выполнения...");
		    dictionary.setProperty("close window","Закрыть окно");
		    dictionary.setProperty("reduce to icon","Свернуть в икону");
		    dictionary.setProperty("save changes?", "Сохранить изменения?");
		    dictionary.setProperty("confirm window closing","Подтверждение закрытия окна");
		    dictionary.setProperty("change background","Изменить фон");
		    dictionary.setProperty("reset background","Обновить фон");

		    dictionary.setProperty("switch","Переключить");
		    dictionary.setProperty("switchmnemonic","S");
		    dictionary.setProperty("window name","Имя окна");
		    dictionary.setProperty("opened windows","Открытые окна");
		    dictionary.setProperty("tile horizontally","Сверху-вниз");
		    dictionary.setProperty("tilehorizontallymnemonic","H");
		    dictionary.setProperty("tile vertically","Слева-направо");
		    dictionary.setProperty("tileverticallymnemonic","V");
		    dictionary.setProperty("cascade","Каскадом");
		    dictionary.setProperty("cascademnemonic","C");
		    dictionary.setProperty("minimize","Минимизировать");
		    dictionary.setProperty("minimizemnemonic","M");
		    dictionary.setProperty("minimize all","Максимизировать");
		    dictionary.setProperty("minimizeallmnemonic","A");

		    // server...
		    dictionary.setProperty("Client request not supported","Запрос клиента не поддержан");
		    dictionary.setProperty("User disconnected","Пользователь отключен");
		    dictionary.setProperty("Updating not performed: the record was previously updated.","Обновление не поддерживается: запись была обновлена .");

		    // wizard...
		    dictionary.setProperty("back","Назад");
		    dictionary.setProperty("next","Вперед");
		    dictionary.setProperty("finish","Закончить");

		    // image panel...
		    dictionary.setProperty("image selection","Выбор изображения");

		    // tip of the day panel...
		    dictionary.setProperty("show 'tip of the day' after launching","Show 'tip of the day' after launching");
		    dictionary.setProperty("previous tip","Previous tip");
		    dictionary.setProperty("next tip","Next tip");
		    dictionary.setProperty("close","Close");
		    dictionary.setProperty("tip of the day","Tip of the day");
		    dictionary.setProperty("select tip","Select tip");
		    dictionary.setProperty("tip name","Tip name");
		    dictionary.setProperty("tips list","Tips list");

		    // progress panel...
		    dictionary.setProperty("progress","Progress");

		    // licence agreement...
		    dictionary.setProperty("i accept the terms in the licence agreement","I accept the terms in the licence agreement");
		    dictionary.setProperty("ok","Ok");
		    dictionary.setProperty("i do not accept the terms in the licence agreement","I do not accept the terms in the licence agreement");

		    // property grid control
		    dictionary.setProperty("property name","Name");
		    dictionary.setProperty("property value","Value");

		    // grid profile
		    dictionary.setProperty("grid profile management","Grid profile management");
		    dictionary.setProperty("restore default grid profile","Restore default grid profile");
		    dictionary.setProperty("create new grid profile","Create new grid profile");
		    dictionary.setProperty("profile description","Profile description");
		    dictionary.setProperty("remove current grid profile","Remove current grid profile");
		    dictionary.setProperty("select grid profile","Select grid profile");
		    dictionary.setProperty("default profile","Default profile");

		    // search box
		    dictionary.setProperty("search","Поиск");
		    dictionary.setProperty("not found","Не найдено");

		    // drag...
		    dictionary.setProperty("drag","Drag");

		    // pivot table...
		    dictionary.setProperty("pivot table settings","Pivot table settings");
		    dictionary.setProperty("row fields","Row fields");
		    dictionary.setProperty("column fields","Column fields");
		    dictionary.setProperty("data fields","Data fields");
		    dictionary.setProperty("filtering conditions","Filtering conditions");
		    dictionary.setProperty("field","Field");
		    dictionary.setProperty("checked","Checked");
		    dictionary.setProperty("at least one data field must be selected","At least one data field must be selected.");
		    dictionary.setProperty("at least one row field must be selected","At least one row field must be selected.");
		    dictionary.setProperty("at least one column field must be selected","At least one column field must be selected.");
		    dictionary.setProperty("expand all","Expand all");
		    dictionary.setProperty("collapse all","Collapse all");


		    resources = new Resources(
		      dictionary,
		      "грн.",
		      '.',
		      ',',
		      Resources.YMD,
		      true,
		      '.',
		      "HH:mm",
		      "EN",
		      true
		    );
		  
	  }


	@Override
	public Resources getResources() {
		return this.resources;
	}


	  /**
	   * Load dictionary, according to the specified language id.
	   * @param langId language id identifier
	   */
	  public final void setLanguage(String langId) throws UnsupportedOperationException {
	    if (!resources.getLanguageId().equals(langId))
	      throw new UnsupportedOperationException("Language identifier not supported.");
	  }


	  /**
	   * @param langId language id identifier
	   * @return internationalization settings, according with the language specified
	   */
	  public final Resources getResources(String langId) throws UnsupportedOperationException {
	    if (!resources.getLanguageId().equals(langId))
	    throw new UnsupportedOperationException("Language identifier not supported.");
	    return resources;
	  }
}
