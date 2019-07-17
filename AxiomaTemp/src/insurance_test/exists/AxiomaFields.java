
package insurance_test.exists;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/** all required fields for Axioma Fields 
 *<table border="1">
 *	<tr>
 *		<td></td>
 *		<th>{@link Necessary}</th>
 *		<th>{@link Type}</th>
 *		<th>Class</th>
 *		<th>размерность</th>
 *		<th>{@link XmlOutputVarType}</th>
 *		<th>Description</th>
 *	</tr>
 *	<tr>
 *		<td><b>Описание</b></td>
 *		<td>Является ли поле обязательным по документации Axioma</td>
 *		<td>Принадлежность к Каско/Осаго</td>
 *		<td>класс для Java ( Database )</td>
 *		<td>размерность поля для базы данных </td>
 *		<td>к какому типу нужно преобразовать значения для вывода в XML </td>
 *		<td>текстовое описание поля, согласно спецификации Аксиома </td>
 *	</tr>
 * 
 *	<tr>
 *		<td><b>Допустимые Значения</b></td>
 *		<td>Required - обязательное, NotRequired - не обязательное</td>
 *		<td>Casco - только для Каско, Osago - только для Осаго, Casco_Osago - для каско и для осаго</td>
 *		<td>значения, допустимые для базы данных </td>
 *		<td>числовое значение ("200") для String, дробное значение ("20.6"/"20,6") для BigDecimal и пустое значение (null, "") для Integer, Object, ... </td>
 *		<td>значения enum {@link XmlOutputVarType}, если null - не выводится в выходной XML </td>
 *		<td>любые текстовые значения </td>
 *	</tr>
 *
 *	<tr>
 *		<td><b>Место использования</b></td>
 *		<td>информационное <small>для обязательности поиска значения при заполнении объекта (в логгировании) </small> </td>
 *		<td>XML-mapping и Java файлы собираются на основании данного поля </td>
 *		<td>XML-mapping и Java генерация <b><small>(если null - поле не отображается в XML, если Object.class - поле не отображается в маппинге на базу )</small></b> </td>
 *		<td>XML-mapping файлы</td>
 *		<td>при генерации выходного XML </td>
 *		<td>информационное ( так же присутствует в генерации Java маппинга ) </td>
 *	</tr>
 *</table>
 *
 * <hr />
 * если были изменения в данном классе - были добавлены/удалены/изменены некоторые Enum, то необходимо:
 * 	<ul>
 * 		<li> сгенерировать Java и XML Mapping  вызовом ua.cetelem.insurance.plugins.axainsur.xml_creator_db.java_generator.GeneratorHibernateMappings </li>
 * 		<li> заменить файлы Java: insurance/src/main/ua.cetelem.insurance.plugins.axainsur.xml_creator_db.model.(ArgeementCasco | AgreementOsago).java </li>
 * 		<li> заменить файлы XML:  silkway/src/ua/cetelem/db/insurance_cardif/daily_report/(AgreementCasco|AgreementOsago).hbm.xml </li>
 * 		<li> ant build, execute hibernate("hbm2ddl.auto")=update </li>
 * 	</ul>
 */
public enum AxiomaFields {
	/** unique identifier for PK of table  */
	id								(Necessary.NotRequired,	Type.Casco_Osago,	Long.class, 		null, 	null, "уникальный номер записи в таблице "),
	/** timestamp of event ( was sended to Cardiff ) */
	sent_to_cardif					(Necessary.NotRequired, 	Type.Casco_Osago,	Date.class, 		null, 	null, "время отправки данных через E-mail"),
	/** sign: Casco ( 2 ) */
	is_casco						(Necessary.NotRequired, 	Type.Casco,			Integer.class, 		null, 	null, "принадлежность страховки к КАСКО"),
	/** sign: Osago ( 3 ) */
	is_osago						(Necessary.NotRequired, 	Type.Osago, 		Integer.class, 		null,	null, "принадлежность страховки к ОСАГО"),
	insurance_id					(Necessary.Required, 	Type.Casco_Osago,	Long.class,			null,	null, "уникальный идентификатор для записи"),
	dossier_status					(Necessary.NotRequired, Type.Casco_Osago,	String.class,		"10",	null, "уникальный идентификатор "),
	ratanet_dossier_id				(Necessary.Required, 	Type.Casco_Osago, 	Long.class, 		null,	XmlOutputVarType.Number, "уникальный номер основной сущности в RataNet"),
	ratanet_insurance_index			(Necessary.Required, 	Type.Casco_Osago, 	Integer.class, 		null,  	XmlOutputVarType.Number, "Индекс страховки, по которой происходит сохранение"),
	// 							Casco/Osago, Type in mapping, DbSize,      XmlConverter,          XmlDescription
	one_payment						(Necessary.Required, 	Type.Casco, 		BigDecimal.class,	"20,6",	XmlOutputVarType.Decimal, "1й платіж, грн."),
	authorization_number_insurer	(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"300",  XmlOutputVarType.String, "№ довіреності страховика, який уклав договір"),
	address_motor					(Necessary.Required, 	Type.Casco, 		String.class, 		"210",  XmlOutputVarType.String, "Адреса Автосалону"),
	address_beneficiary				(Necessary.Required, 	Type.Casco, 		String.class, 		"150",  XmlOutputVarType.String, "Адреса Вигодонабувача"),
	bt_dtp							(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.String, "БТ ДТП"),
	bt_etc_risk						(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.String, "БТ інші ризики"),
	bt_driving_away					(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.String, "БТ угін"),
	accessories_price_one			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 1"),
	accessories_price_ten			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 10"),
	accessories_price_two			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 2"),
	accessories_price_three			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 3"),
	accessories_price_four			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 4"),
	accessories_price_five			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 5"),
	accessories_price_six			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 6"),
	accessories_price_seven			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 7"),
	accessories_price_eight			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 8"),
	accessories_price_nine			(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Вартість ДО 9"),
	beneficiary						(Necessary.Required, 	Type.Casco, 		String.class, 		"250",  XmlOutputVarType.String, "Вигодонабувач"),
	view_involvement 				(Necessary.Required, 	Type.Casco_Osago, 	Integer.class, 		null,  	XmlOutputVarType.Number, "Вид залучення"),
	insurance_type					(Necessary.NotRequired, 	Type.Casco_Osago, 	Integer.class, 		null,  	XmlOutputVarType.Number, "Вид страхування"),
	date_mortgage_contract			(Necessary.NotRequired, Type.Casco, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата договору застави"),
	date_expiry_loan_agreement		(Necessary.NotRequired, Type.Casco, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата закінчення дії кредитного договору"),
	date_birth_beneficiary			(Necessary.Required, 	Type.Casco, 		Date.class, 		null, 	XmlOutputVarType.Date, "Дата народження вигодонабувача"),
	date_of_warrant					(Necessary.Required, 	Type.Casco_Osago, 	Date.class, 		"",  	XmlOutputVarType.Date, "Дата оформлення довіреності"),
	date_loan_agreement				(Necessary.NotRequired, Type.Casco, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата початку дії кредитного договору"),
	date_of_contract				(Necessary.Required, 	Type.Casco, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата укладення договору"),
	real_cost_car					(Necessary.Required, 	Type.Casco, 		BigDecimal.class,	"20.6", XmlOutputVarType.Decimal, "Дійсна вартість авто, грн."),
	real_cost_car_accessories		(Necessary.Required, 	Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Дійсна вартість авто з урахуванням ДО, грн."),
	accessories_one					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 1"),
	accessories_ten					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 10"),
	accessories_two					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 2"),
	accessories_three				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 3"),
	accessories_four				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 4"),
	accessories_five				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 5"),
	accessories_six					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 6"),
	accessories_seven				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 7"),
	accessories_eight				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 8"),
	accessories_nine				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ДО 9"),
	accessories_one_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500", 	XmlOutputVarType.String, "ДО 1, інше"),
	accessories_ten_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 10, інше"),
	accessories_two_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 2, інше"),
	accessories_three_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 3, інше"),
	accessories_four_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 4, інше"),
	accessories_five_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 5, інше"),
	accessories_six_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 6, інше"),
	accessories_seven_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 7, інше"),
	accessories_eight_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 8, інше"),
	accessories_nine_inshe			(Necessary.NotRequired, Type.Casco, 		String.class, 		"500",  XmlOutputVarType.String, "ДО 9, інше"),
	edrpou_motor					(Necessary.NotRequired, Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.String, "ЄДРПОУ Автосалону"),
	total_cost_aq					(Necessary.NotRequired, Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Загальна вартість ДО, грн."),
	protection_one					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 1"),
	protection_two					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 2"),
	protection_three				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 3"),
	protection_four					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 4"),
	protection_five					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 5"),
	protection_six					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 6"),
	protection_seven				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 7"),
	protection_eight				(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 8"),
	protection_nine					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 9"),
	protection_ten					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Засіб захисту 10"),
	/** not filled  */
	protection_one_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 1, інше"),
	/** not filled  */
	protection_ten_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 10, інше"),
	/** not filled  */
	protection_two_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 2, інше"),
	/** not filled  */
	protection_three_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 3, інше"),
	/** not filled  */
	protection_four_inshe			(Necessary.NotRequired, Type.Casco,			Object.class,  		"",  	XmlOutputVarType.String, "Засіб захисту 4, інше"),
	/** not filled  */
	protection_five_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 5, інше"),
	/** not filled  */
	protection_six_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 6, інше"),
	/** not filled  */
	protection_seven_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 7, інше"),
	/** not filled  */
	protection_eight_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 8, інше"),
	/** not filled  */
	protection_nine_inshe			(Necessary.NotRequired, Type.Casco,			Object.class, 		"",  	XmlOutputVarType.String, "Засіб захисту 9, інше"),
	losses_during_year_insurance	(Necessary.Required, 	Type.Casco, 		String.class, 		"100",  XmlOutputVarType.Number, "Збитки протягом попереднього року страхування"),
	casco_category_vehicle			(Necessary.Required, 	Type.Casco, 		String.class, 		"100",  XmlOutputVarType.String, "Категорія ТЗ"),
	number_remedies					(Necessary.NotRequired, Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Кількість засобів захисту"),
	accessories_sum					(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Кількість одиниць ДО"),
	end_of_contract					(Necessary.Required, 	Type.Casco_Osago, 	Date.class, 		null,  	XmlOutputVarType.Date, "Кінец дії договору"),
	code_insurance_contract			(Necessary.Required, 	Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.String, "Код договору страхування"),
	coeff_damage					(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Decimal, "Коефіцієнт збитковості"),
	coeff_reg						(Necessary.Required, 	Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Number, "Коефіціент регіону"),
	coeff_lifetime					(Necessary.Required, 	Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Number, "Коефіціент строку експлуатації"),
	car_color						(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Колір авто"),
	e_mail							(Necessary.NotRequired, Type.Casco_Osago, 	String.class, 		"50",  	XmlOutputVarType.String, "Контактний E-Mail"),
	phone							(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"10",  	XmlOutputVarType.String, "Контактний телефон"),
	phone_insurer					(Necessary.NotRequired, Type.Casco_Osago, 	String.class, 		"10",  	XmlOutputVarType.String, "Контактний телефон страховика, який уклав договір"),
	number_of_sap					(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"32",  	XmlOutputVarType.String, "Логін в САП"),
	casco_brand_model_car			(Necessary.Required, 	Type.Casco, 		String.class, 		"30",  	XmlOutputVarType.String, "Марка авто"),
	casco_brand_model_car_etc		(Necessary.NotRequired, Type.Casco, 		String.class, 		"30",  	XmlOutputVarType.String, "Марка авто(інше)"),
	casco_brand_retailer_car		(Necessary.Required, 	Type.Casco, 		String.class, 		"100",  XmlOutputVarType.String, "Модель авто"),
	casco_brand_retailer_car_etc	(Necessary.NotRequired, Type.Casco, 		String.class, 		"100",  XmlOutputVarType.String, "Модель авто(інше)"),
	name_type_of_business			(Necessary.NotRequired, Type.Casco_Osago,	String.class, 		"50",  	XmlOutputVarType.String, "Назва виду бізнесу"),
	name_branches_where_contract	(Necessary.NotRequired, Type.Casco_Osago,	String.class, 		"40",  	XmlOutputVarType.String, "Назва відділення, на якому укладено договір"),
	insurance_program				(Necessary.NotRequired, 	Type.Casco_Osago,	Integer.class, 		"",  	XmlOutputVarType.Number, "Назва продукту"),
	fraud							(Necessary.Required, 	Type.Casco_Osago, 	Integer.class, 		null,  	XmlOutputVarType.Number, "Наявність, відсутність спроб шахрайства, підстав для регресу"),
	availability_product_prot		(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Наявність засобу(ів) захисту", "availability_product_protection"), // name is too long for Oracle 
	number_branches_sap_service		(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"4",  	XmlOutputVarType.String, "Номер відділення в САП, на якому обслуговється договір"),
	number_branches_sap_concluded	(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"4",  	XmlOutputVarType.String, "Номер відділення в САП, на якому укладено договір"),
	number_collateral_agreement		(Necessary.NotRequired, Type.Casco, 		String.class, 		"30",  	XmlOutputVarType.String, "Номер договору застави"),
	number_deal_casco				(Necessary.Required, 	Type.Casco, 		String.class, 		"255",  XmlOutputVarType.String, "Номер договору страхування"),
	number_credit_agreement			(Necessary.NotRequired, Type.Casco_Osago, 	String.class, 		"30",  	XmlOutputVarType.String, "Номер кредитного договору"),
	number_body_chassis				(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"60",  	XmlOutputVarType.String, "Номер кузова/шасі"),
	license_plate					(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"20",  	XmlOutputVarType.String, "Номерний знак"),
	engine_volume					(Necessary.NotRequired, 	Type.Casco, 		String.class, 		"100", 	XmlOutputVarType.Number, "Об'єм двигуна, куб.см"),
	basis_calculation_insurance		(Necessary.Required, 	Type.Casco, 		String.class, 		"100",  XmlOutputVarType.Number, "Основа розрахунку страхового відшкодування (СТО)"),
	period_of_insurance				(Necessary.Required, 	Type.Casco_Osago, 	Integer.class, 		null,  	XmlOutputVarType.Number, "Період страхування"),
	full_name_of_insurer			(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"128",  XmlOutputVarType.String, "ПІБ Страховика, який уклав договір"),
	reason_appointment				(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Підстава призначення"),
	reason_appointment_one			(Necessary.NotRequired, Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.String, "Підстава призначення (інше)"),
	full_address_of_insurer			(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"210",  XmlOutputVarType.String, "Повна адреса страховика, який уклав договір"),
	name_motor						(Necessary.Required, 	Type.Casco, 		String.class, 		"40",  	XmlOutputVarType.String, "Повна назва Автосалону"),
	beginning_of_contract			(Necessary.Required, 	Type.Casco_Osago, 	Date.class, 		null,  	XmlOutputVarType.Date, "Початок дії договору"),
	premium_one_year				(Necessary.Required, 	Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Премія 1, на 1 рік, в грн."),
	mileage							(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Decimal, "Пробіг, км"),
	casco_ins_program				(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Программа страхування"),
	year_of_construction			(Necessary.Required, 	Type.Casco_Osago, 	Long.class, 		null,  	XmlOutputVarType.Number, "Рік виготовлення"),
	term_loan_months				(Necessary.NotRequired, Type.Casco, 		Long.class, 		"",  	XmlOutputVarType.Decimal, "Срок кредиту, місяців"),
	insured_status					(Necessary.Required, 	Type.Casco_Osago, 	Integer.class, 		null,  	XmlOutputVarType.Number, "Статус страхувальника"),
	insurance_summ_hrn				(Necessary.Required, 	Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Страхова сума, в грн"),
	insured_house					(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"15",  	XmlOutputVarType.String, "Страхувальник - Будинок"),
	insured_street					(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"60",  	XmlOutputVarType.String, "Страхувальник - Вулиця"),
	insured_date_issue				(Necessary.Required, 	Type.Casco_Osago, 	Date.class, 		null,  	XmlOutputVarType.Date, "Страхувальник - Дата видачі паспорта"),
	insured_date_birth				(Necessary.Required, 	Type.Casco_Osago, 	Date.class, 		"",  	XmlOutputVarType.Date, "Страхувальник - Дата народження"),
	insured_registered				(Necessary.NotRequired, Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.Date, "Страхувальник - Дата реєстрації"),
	insured_TIN						(Necessary.Required, 	Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.Number, "Страхувальник - Ідентифікаційний код"),
	insured_first_name				(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"60",  	XmlOutputVarType.String, "Страхувальник - Ім'я"),
	insured_apartment				(Necessary.NotRequired, Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.String, "Страхувальник - Квартира"),
	insured_issuing_authority		(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"100",  XmlOutputVarType.String, "Страхувальник - Ким виданий паспорт"),
	insured_town					(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"30",  	XmlOutputVarType.String, "Страхувальник - Населений пункт"),
	insured_passport				(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"8",  	XmlOutputVarType.Number, "Страхувальник - Номер паспорта"),
	insured_scope					(Necessary.NotRequired, Type.Casco_Osago, 	String.class, 		"30",  	XmlOutputVarType.String, "Страхувальник - Область"),
	insured_middle_name				(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"60",  	XmlOutputVarType.String, "Страхувальник - По-батькові"),
	insured_zip_code				(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"5",  	XmlOutputVarType.String, "Страхувальник - Поштовий індекс"),
	insured_last_name				(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"40",  	XmlOutputVarType.String, "Страхувальник - Прізвище"),
	insured_series_passport			(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"8",  	XmlOutputVarType.String, "Страхувальник - Серія паспорта"),
	insured_zip_code_fact			(Necessary.NotRequired, Type.Casco, 		String.class, 		"5",  	XmlOutputVarType.String, "Страхувальник - Поштовий індекс (фактична адреса)"),
	insured_scope_scope_fact		(Necessary.NotRequired, Type.Casco, 		String.class, 		"30",  	XmlOutputVarType.String, "Страхувальник - Область (фактична адреса)"),
	insured_town_fact				(Necessary.NotRequired, Type.Casco, 		String.class, 		"30",  	XmlOutputVarType.String, "Страхувальник - Населений пункт (фактична адреса)"),
	insured_street_fact				(Necessary.NotRequired, Type.Casco, 		String.class, 		"60",  	XmlOutputVarType.String, "Страхувальник - Вулиця (фактична адреса)"),
	insured_house_fact				(Necessary.NotRequired, Type.Casco, 		String.class, 		"15",  	XmlOutputVarType.String, "Страхувальник - Будинок (фактична адреса)"),
	insured_apartment_fact			(Necessary.NotRequired, Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.String, "Страхувальник - Квартира (фактична адреса)"),
	payment_due_date_one			(Necessary.Required, 	Type.Casco, 		Date.class, 		"",  	XmlOutputVarType.Date, "Строк сплати 1 платежу"),
	casco_agreement_term			(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Строк страхування"),
	scheduled_number				(Necessary.Required, 	Type.Casco_Osago, 	String.class, 		"10",  	XmlOutputVarType.String, "Табельний номер"),
	tariff_one_for_one_year			(Necessary.Required, 	Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Тариф 1 на 1 рік, в %"),
	territory_action_casco			(Necessary.Required, 	Type.Casco, 		String.class, 		"100",  XmlOutputVarType.Number, "Територія дії КАСКО"),
	tz_is_object_pledge				(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ТЗ є об`єктом застави"),
	type_business_partner			(Necessary.Required, 	Type.Casco_Osago, 	Integer.class, 		"",  	XmlOutputVarType.Number, "Тип ділового партнера"),
	payment_conditions				(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Умова сплати страхової премії"),
	fax								(Necessary.NotRequired, Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.String, "Факс"),
	franchise_liability_insurance	(Necessary.Required, 	Type.Casco, 		String.class, 		"100",  XmlOutputVarType.Number, "Франшиза по іншим ризикам, в %"),
	franchise_property_insurance	(Necessary.Required, 	Type.Casco, 		Float.class, 		null,  	XmlOutputVarType.Number, "Франшиза по угону, в %"),
	insure_accessories				(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Чи потрібно страхувати ДО?"),
	bonus_malus						(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Бонус-Малус"),
	driving_experience				(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Водійський стаж (повних років)"),
	driving_experience_insured		(Necessary.Required, 	Type.Osago, 		String.class, 		"4",  	XmlOutputVarType.Date, "Водійський стаж з"),
	driving_experience_one			(Necessary.NotRequired, Type.Osago, 		String.class, 		"4", 	XmlOutputVarType.Date, "Водійський стаж з водія №1"),
	driving_experience_two			(Necessary.NotRequired, Type.Osago, 		String.class, 		"4", 	XmlOutputVarType.Date, "Водійський стаж з водія №2"),
	driving_experience_three		(Necessary.NotRequired, Type.Osago, 		String.class, 		"4", 	XmlOutputVarType.Date, "Водійський стаж з водія №3"),
	driving_experience_four			(Necessary.NotRequired, Type.Osago, 		String.class, 		"4", 	XmlOutputVarType.Date, "Водійський стаж з водія №4"),
	date_birth_one					(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата народження водія №1"),
	date_birth_two					(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата народження водія №2"),
	date_birth_three				(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата народження водія №3"),
	date_birth_four					(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата народження водія №4"),
	date_enters_loan_agreement		(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата оформлення кредитного договору"),
	date_of_conclusion				(Necessary.Required, 	Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата укладення договору"),
	area							(Necessary.Required, 	Type.Osago, 		String.class, 		"10",  	XmlOutputVarType.Number, "Зона (1-7)"),
	coeff_k1						(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "К1(По типу транспортних засобів)"),
	coeff_k2						(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "К2 (Територія використання ТЗ)"),
	coeff_k3						(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "К3 (Сфера використання ТЗ)"),
	coeff_k4						(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "К4 (Мінімальний водійський стаж)"),
	coeff_k5						(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "К5 (Кількість допущенних осіб)"),
	coeff_k6						(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "К6 (Наявність-відсутність спроб шахрайства)"),
	coeff_k7						(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "К7 (Короткостроковість)"),
	category_agreement				(Necessary.Required, 	Type.Osago, 		String.class, 		"10",  	XmlOutputVarType.String, "Категорія договору"),
	osago_category_vehicle			(Necessary.Required, 	Type.Osago, 		String.class, 		"10",  	XmlOutputVarType.String, "Категорія ТЗ"),
	flat							(Necessary.NotRequired, Type.Osago,			String.class, 		"10", 	XmlOutputVarType.String, "Квартира"),
	issued_drivers_license_insur	(Necessary.Required, 	Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Ким видане посвідчення водія", "issued_drivers_license_insured"),
	issued_drivers_license_one		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Ким видане посвідчення водія №1"),
	issued_drivers_license_two		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Ким видане посвідчення водія №2"),
	issued_drivers_license_three	(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Ким видане посвідчення водія №3"),
	issued_drivers_license_four		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Ким видане посвідчення водія №4"),
	number_persons_spec_contract	(Necessary.NotRequired, Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Кількість зазначених осіб в договорі"),
	count_person					(Necessary.NotRequired, Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Кількість осіб, правова відповідальність котрих застрахована цим договором, крім страхувальника"),
	when_issued_drivers_lic_insur	(Necessary.Required, 	Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Коли видане посвідчення водія", "when_issued_drivers_lic_insured"),
	when_issued_drivers_lic_one		(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Коли видане посвідчення водія №1"),
	when_issued_drivers_lic_two		(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Коли видане посвідчення водія №2"),
	when_issued_drivers_lic_three	(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Коли видане посвідчення водія №3"),
	when_issued_drivers_lic_four	(Necessary.NotRequired, Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Коли видане посвідчення водія №4"),
	limit_liability_for_damage_pr	(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.String, "Ліміт відповідальності за шкоду майну (на одного потерпілого)", "limit_liability_for_damage_property"),
	limit_liability_for_damage_lv	(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.String, "Ліміт відповідальності за шкоду, заподіяну життю та здоров'ю (на одного потерпілого)", "limit_liability_for_damage_live"),
	osago_brand_model_car			(Necessary.Required, 	Type.Osago, 		String.class, 		"130",  XmlOutputVarType.String, "Марка, модель автомобіля"),
	registration_place				(Necessary.Required, 	Type.Osago, 		String.class, 		"150",  XmlOutputVarType.String, "Місце реєстрації"),
	number_agreement				(Necessary.Required, 	Type.Osago, 		String.class, 		"255",  XmlOutputVarType.String, "Номер договору страхування"),
	drivers_license_number_insur	(Necessary.Required, 	Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Номер посвідчення водія", "drivers_license_number_insured"),
	drivers_license_number_one		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Номер посвідчення водія №1"),
	drivers_license_number_two		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Номер посвідчення водія №2"),
	drivers_license_number_three	(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Номер посвідчення водія №3"),
	drivers_license_number_four		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Номер посвідчення водія №4"),
	number_specialchar				(Necessary.Required, 	Type.Osago, 		String.class, 		"80",  	XmlOutputVarType.String, "Номер спецзнаку"),
	driver_fullname_one				(Necessary.NotRequired, Type.Osago, 		String.class, 		"300",  XmlOutputVarType.String, "ПІБ водія №1"),
	driver_fullname_two				(Necessary.NotRequired, Type.Osago, 		String.class, 		"300",  XmlOutputVarType.String, "ПІБ водія №2"),
	driver_fullname_three			(Necessary.NotRequired, Type.Osago, 		String.class, 		"300",  XmlOutputVarType.String, "ПІБ водія №3"),
	driver_fullname_four			(Necessary.NotRequired, Type.Osago, 		String.class, 		"300",  XmlOutputVarType.String, "ПІБ водія №4"),
	full_registered_address_cl		(Necessary.Required, 	Type.Osago, 		String.class, 		"110",  XmlOutputVarType.String, "Повна адреса прописки клієнта", "full_registered_address_client"),
	payment							(Necessary.Required, 	Type.Osago, 		String.class, 		"50",  	XmlOutputVarType.String, "Призначення платежу"),
	osago_payment					(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Прикінцевий платіж"),
	osago_ins_program				(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Програма страхування"),
	rates_may						(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Резидентність"),
	series_of_driving_lic_insured	(Necessary.Required, 	Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Серія посвідчення водія"),
	series_of_driving_lic_one		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Серія посвідчення водія №1"),
	series_of_driving_lic_two		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Серія посвідчення водія №2"),
	series_of_driving_lic_three		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Серія посвідчення водія №3"),
	series_of_driving_lic_four		(Necessary.NotRequired, Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Серія посвідчення водія №4"),
	series_specialchar				(Necessary.Required, 	Type.Osago, 		String.class, 		"80",  	XmlOutputVarType.String, "Серія спецзнаку"),
	insurance_premium				(Necessary.Required, 	Type.Osago, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Страхова премія, в грн."),
	insured_tin						(Necessary.Required, 	Type.Osago, 		String.class, 		"10",  	XmlOutputVarType.Number, "Страхувальник - Ідентифікаційний код"),
	date_payment_insurance_prem		(Necessary.Required, 	Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Строк сплати страхового платежу","date_payment_insurance_premium"),
	agreement_term					(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Строк дії договору"),
	scope_of_specification			(Necessary.Required, 	Type.Osago,			Object.class,		"",  	XmlOutputVarType.Number, "Сфера використання ТЗ"),
	type_of_use						(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.String, "Тип використання"),
	type_of_vehicle					(Necessary.Required, 	Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Тип ТЗ"),
	type_of_vehicle_one				(Necessary.Required, 	Type.Osago, 		String.class, 		"100",  XmlOutputVarType.String, "Тип транспортного засобу"),
	franchise						(Necessary.Required, 	Type.Osago, 		Float.class, 		null,  	XmlOutputVarType.Decimal, "Франшиза"),
	start_time_of_contract			(Necessary.Required, 	Type.Osago, 		Date.class, 		null,  	XmlOutputVarType.Time, "Час початку дії договору"),
	number_agreement_casko			(Necessary.NotRequired, Type.Osago, 		String.class, 		"255",  XmlOutputVarType.String, "Номер договору КАСКО"),
	full_name_of_insurer_one		(Necessary.Required, 	Type.Osago, 		String.class, 		"128",  XmlOutputVarType.String, "ПІБ Страховика, який обслуговує договір"),
	code_insurer_who_contracted		(Necessary.Required, 	Type.Casco, 		String.class, 		"10",  	XmlOutputVarType.String, "Код TY страховика, який уклав договір"),
	usd_nbu							(Necessary.Required, 	Type.Casco, 		Float.class, 		null,  	XmlOutputVarType.String, "Курс доллара, згідно НБУ"),
	insurance_summ_usd				(Necessary.Required, 	Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Страхова сума, в долларах"),
	used_like_taxi					(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Чи використовується як таксі"),
	real_cost_car_accessories_usd	(Necessary.Required, 	Type.Casco, 		BigDecimal.class, 	"20.6", XmlOutputVarType.Decimal, "Дійсна вартість авто з урахуванням ДО, в доларах США*"),
	registered_ukr					(Necessary.Required, 	Type.Osago, 		Integer.class, 		null,  	XmlOutputVarType.Number, "ТЗ зареєстровано в Україні"),
	series_of_insurance_contract	(Necessary.Required, 	Type.Osago, 		String.class, 		"60",  	XmlOutputVarType.String, "Серія договору страхування"),
	casco_franchise					(Necessary.Required, 	Type.Casco, 		Integer.class, 		null,  	XmlOutputVarType.Number, "Франшиза по ДТП, в %"),	
/** skipped - not required, may be need  */
	notes							(Necessary.NotRequired, Type.Osago, 		Object.class, 		null,  	XmlOutputVarType.String, "Примітки"),
/** skipped - not required, may be need  */
	privileged_category_one			(Necessary.NotRequired, Type.Osago, 		Object.class, 		null,  	XmlOutputVarType.String, "П (Пільгова категорія)"),
/** skipped - not required */ 	
	full_address_of_insurer_one		(Necessary.NotRequired, Type.Osago, 		Object.class, 		null,  	XmlOutputVarType.String, "Повна адреса страховика, який обслуговує договір"),
/** skipped - not required, may be need  */ 	
	phone_insurer_one				(Necessary.NotRequired, Type.Osago, 		Object.class, 		null,  	XmlOutputVarType.String, "Контактний телефон страховика, який обслуговує договір"),
/** skipped - not required, may be need  */ 	
	payment_due_date_two			(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "Строк сплати 2 платежу"),
/** skipped - not required, may be need  */
	payment_due_date_three			(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "Строк сплати 3 платежу"),
/** skipped - not required, may be need  */	
	payment_due_date_four			(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "Строк сплати 4 платежу"),
/** skipped - not required, may be need  */
	identification_privileged_cat	(Necessary.NotRequired, Type.Osago,			Object.class, 		"",  	XmlOutputVarType.String, "Ідентифікаційні параметри документа що підтверджує пільгову категорію (назва, номер, дата, коли і ким виданий)", "identification_privileged_categories"),
/** skipped - not required, may be need  */ 	
	name_tu_where_contract			(Necessary.NotRequired, Type.Casco_Osago, 	Object.class, 		null,  	XmlOutputVarType.String, "Назва ТУ, на якому укладено договір"),
/** skipped field, agreed */
	two_payment 					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	three_payment 					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	four_payment 					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	coeff_twoyear					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	coeff_threeyear					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	coeff_fouryear					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	coeff_fiveyear					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	coeff_sixyear					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	coeff_sevenyear					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	coeff_oneyear					(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	tariff_one_to_two_years			(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	tariff_two_to_two_years			(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
/** skipped field, agreed */
	tariff_three_for_two_year		(Necessary.NotRequired, Type.Casco, 		Object.class, 		null,  	XmlOutputVarType.String, "skipped field"),
	
	fatf_surname 					(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"40",  	XmlOutputVarType.String, "Призвище"),
	fatf_first_name 				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"60",  	XmlOutputVarType.String, "Ім’я"),
	fatf_patronymic_name 			(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"60",  	XmlOutputVarType.String, "По-батькові"),
	fatf_birthday 					(Necessary.NotRequired, Type.Casco_Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, "Дата народження (день , місяць рік)"),
	fatf_citizenship 				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"10",  	XmlOutputVarType.String, "Громадянство"),
	fatf_person_tin					(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"10",  	XmlOutputVarType.String, "№ облікової картки платника податку"),
	fatf_address_reg				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"185", 	XmlOutputVarType.String, "Країна , поштовій індекс, область, Населенній пункт, вулиця, будинок, корпус(споруда), квартира (Адресса місця реєСтрації резидента)"),
	fatf_address_temp				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"185", 	XmlOutputVarType.String, "Країна , поштовій індекс, область,Населенній пункт, вулиця, будинок,корпус(споруда), квартира (Адресса місця тимЧасового перебування резидента)"),
	fatf_document_type				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"30",  	XmlOutputVarType.String, "Вид документа що посвідчує фізичну Особу"),
	fatf_document_number			(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"8",  	XmlOutputVarType.String, "Серія та номер докумнта, що посвідчує Фізичну особу"),
	fatf_document_date				(Necessary.NotRequired, Type.Casco_Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, 	 "Дата видачі документа , що посвідчує Фізичну особу"),
	fatf_document_agency			(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"100", 	XmlOutputVarType.String, "Повна назва органу , який відав документ, що посвідчує Фізичну особу"),
	fatf_person_terrorist			(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "Пов’язаність із здійсненням Терористичної діяльності"),
	fatf_person_foreign_senior		(Necessary.NotRequired, Type.Casco_Osago, 		String.class,		"5",	XmlOutputVarType.String, "Принадлежність , пов’язаність з Іноземними високо посадовцями (публічними діячами) ","fatf_person_foreign_senior_official"),
	fatf_future_service				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"20",  	XmlOutputVarType.String, "мета майбутніх ділових відносин  Зі Страхувальником "),
	fatf_insurance_payment_valid	(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "Розмір страхового платежу , що Сплачуеться Страхувальником відповідає Наявній інформації про зміст Його діяльності та фінансовій стан."),
	fatf_future_deals				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"20",  	XmlOutputVarType.String, "Характер майбутніх ділових відносин зі Страхувальником "),
	fatf_risk_level					(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"10",  	XmlOutputVarType.String, "Рівень ризику(низький,середній або високий)"),
	fatf_person_document_valid		(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "вказані у Розділі 1-му відомості про фізичну особу Відповідають відомостями що містяться у поданних Фізичною особою офіційних документах або Засвідченних в установленному порядку їх копіях "),
	fatf_ex_rdusb					(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"10",  	XmlOutputVarType.String, "Страховик що підписав угоду: Підрозділ "),
	fatf_user_name					(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"128", 	XmlOutputVarType.String, "Страховик що підписав угоду: ПІБ"),
	fatf_contract_signature			(Necessary.NotRequired, Type.Casco_Osago, 		Date.class, 		null,  	XmlOutputVarType.Date, 	 "Страховик що підписав угоду : Дата"),
	fatf_risk_citizenship			(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "Особа є громадянином або зареєстрованна В країні, яка включенна до переліку Ризи Кованих країн "),
	fatf_risk_operation_foreign		(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "соба має намір переказати кошти з Країни або отримати кошти в країні ,яка  включенна до переліку Ризикованих країн "),
	fatf_risk_operation				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "Особа наполягає на проведенні операції За правилами, відміннмим від встановленних за Конодавством та внутр докум Компанії  Щодо таких операцій за змістом , або за строками Її проведення "),
	fatf_fraud_doubt				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "Що до особи існують сумніви у Достовірності поданих нею документів Або раніше наданих ідентифікаційних данних "),
	fatf_false_documents			(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "Особа надає непрвдиві ідентифікаційні Данні "),
	fatf_unchecked_documents		(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "Особа надає інформацію яку неможливо  Перевірити "),
	fatf_less_documents				(Necessary.NotRequired, Type.Casco_Osago, 		String.class, 		"5",  	XmlOutputVarType.String, "Особа не надає відомості , передбачені законодавством та відповідними Внутрішніми докумнтами"),
	;

	/** destination of field: Casco, Osago, Casco/Osago */
	private Type type=null;
	/** Class of field for set properties - for create get-er and set-er */
	private Class<?> javaClass=null;
	/** size in Db:
	 * <ul>
	 * 	<li><b>null</b> - not sized </li>
	 * 	<li><b> empty string </b> - not sized </li>
	 * 	<li><b> 250 -==- length="250"</b> - for varchar values </li>
	 * 	<li><b> 20,6 -==- precision="20" scale="6" </b> - for varchar values </li>
	 * </ul>
	 *  */
	private String dbSize;
	/** type of variable for Java/Hibernate  */
	private XmlOutputVarType xmlOutputVarType;
	/** description for XML file  */
	private String xmlOutputDescription;
	/** XML element name  */
	private String xmlOutputElementName;
	
	private Necessary necessary;
	
	/** enum constructor  
	 * @param type - type of element 
	 * @param javaClass - class of field in java mapping ( Object.class - - field always empty, always equals null, not need to mapped to Database)
	 * @param dbSize - size for output in database Hibernate ( for String ="xxx", for BigDecimal "xx.yy" where xx - precision, yy - scale ) 
	 * @param xmlOutputVarType - var type of variable ( if null - not mapped to XML element )
	 * @param xmlOutputDescription -  description of variable for xml
	 */
	AxiomaFields(Necessary necessary, Type type, Class<?> javaClass, String dbSize, XmlOutputVarType xmlOutputVarType, String xmlOutputDescription){
		this(necessary, type,javaClass, dbSize, xmlOutputVarType, xmlOutputDescription, null);
	}

	/** enum constructor
	 * {@inheritDoc}  
	 * @param type - type of element 
	 * @param javaClass - class of field in java mapping ( Object.class - - field always empty, always equals null, not need to mapped to Database)
	 * @param dbSize - size for output in database Hibernate ( for String ="xxx", for BigDecimal "xx.yy" where xx - precision, yy - scale ) 
	 * @param xmlOutputVarType - var type of variable ( if null - not mapped to XML element )
	 * @param xmlOutputDescription -  description of variable for xml
	 * @param xmlOutputElementName - name of xml element ( if null then xmlOutputElementName=field.name )
	 */
	AxiomaFields(Necessary necessary, 
				 Type type, 
				 Class<?> javaClass, 
				 String dbSize, 
				 XmlOutputVarType xmlOutputVarType, 
				 String xmlOutputDescription, 
				 String xmlOutputElementName){
		this.necessary=necessary;
		this.type=type;
		this.javaClass=javaClass;
		this.dbSize=dbSize;
		this.xmlOutputVarType=xmlOutputVarType;
		this.xmlOutputDescription=xmlOutputDescription;
		if(xmlOutputElementName==null){
			this.xmlOutputElementName=this.name();
		}else{
			this.xmlOutputElementName=xmlOutputElementName;
		}
	}
	
	/** type of Java class for create Java  Hibernate mapping */
	public Class<?> getJavaClass(){
		return this.javaClass;
	}
	
	/** type of element in finally XML file for Axioma  */
	public XmlOutputVarType getXmlOutputVarType() {
		return xmlOutputVarType;
	}

	/** Description from Axioma classificator  */
	public String getXmlOutputDescription() {
		return xmlOutputDescription;
	}

	/** Xml tag name (in finally Xml output file ) */
	public String getXmlOutputElementName() {
		return xmlOutputElementName;
	}

	/** get type of this field {@link Type} */
	public Type getType(){
		return type;
	}
	
	/**
	 * is field are required by Axioma classification
	 * @return
	 */
	public boolean isRequired(){
		return Necessary.Required.equals(this.necessary);
	}
	
	/** type of Element - for Casco only, for Osago only, for Casco and for Osago  */
	public static enum Type{
		Casco,
		Osago,
		Casco_Osago
	}

	/** Necessary in Axioma Specification  */
	public static enum Necessary{
		/** value is required  */
		Required,
		/** value is not required  */
		NotRequired
	}
	
	/** type of element in XML output text file  */
	public static enum XmlOutputVarType{
		/** String converter - represent in XML element string value */
		String	("emptyConverter"),
		/** Boolean converter - represent in XML element boolea value: true, false */
		N		("booleanConverter"),
		/** Number converter - represent in XML element integer value: 123 */
		Number	("numberConverter"),
		/** Number converter - represent in XML element float value: 123.45 */
		Decimal ("decimalConverter"),
		/** Date converter - represent in XML element date value: dd.MM.yyyy */
		Date 	("dateConverter"),
		/** Time converter - represent in XML element time value: dd.MM.yyyy */
		Time	("timeConverter"),
		/** Datetime converter - represent in XML element time value: dd.MM.yyyy hh:mi */
		Datetime("datetimeConverter");
		
		
		private String nameOfConverter=null;
		
		private XmlOutputVarType(String nameOfConverter) {
			this.nameOfConverter=nameOfConverter;
		}
		
		public String getNameOfConverter(){
			return this.nameOfConverter;
		}
		
	}

	/** get {@link AxiomaFields} by name  */
	public static AxiomaFields getAxiomaFieldsByName(String name){
		try{
			return AxiomaFields.valueOf(name);
		}catch(Exception ex){
			return null;
		}
	}

	private String hibernateType=null;
	/**
	 * return full Hibernate type for field:
	 * 
	 * @return
	 */
	public String getHibernateType(){
		if(this.hibernateType==null){
			if(String.class.equals(this.javaClass)){
				this.hibernateType=" type=\"string\" length=\""+this.dbSize+"\" ";
			}else if(BigDecimal.class.equals(this.javaClass)){
				if(this.dbSize==null){
					this.hibernateType=" type=\"big_decimal\" precision=\"20\" scale=\"6\" ";
				}else{
					this.dbSize=this.dbSize.trim().replaceAll("[, -]", ".");
					int delimiter=this.dbSize.indexOf('.');
					if(delimiter>=0){
						this.hibernateType=" type=\"big_decimal\" precision=\""+this.dbSize.substring(0,delimiter)+"\" scale=\""+this.dbSize.substring(delimiter+1)+"\" ";
					}else{
						this.hibernateType=" type=\"big_decimal\" precision=\""+this.dbSize+"\" ";
					}
				}
			}else if(Long.class.equals(this.javaClass)){
				this.hibernateType=" type=\"long\" ";
			}else if(Float.class.equals(this.javaClass)){
				this.hibernateType=" type=\"float\" ";
			}else if(Date.class.equals(this.javaClass)){
				if(this.xmlOutputVarType==null){
					this.hibernateType=" type=\"timestamp\" ";
				}else if(this.xmlOutputVarType.equals(XmlOutputVarType.Time)){
					this.hibernateType=" type=\"time\" ";
				}else if(this.xmlOutputVarType.equals(XmlOutputVarType.Date)){
					this.hibernateType=" type=\"date\" ";
				}else{
					this.hibernateType=" type=\"timestamp\" ";
				}
			}else if(Integer.class.equals(this.javaClass)){
				this.hibernateType=" type=\"integer\" ";
			}else{
				this.hibernateType="  ";
				System.err.println("AxiomaFields#getHibernateType does not recognize the class: "+this.javaClass);
				// throw new RuntimeException();
			}
		}
		return this.hibernateType;
	}
	
	/** get {@link AxiomaFields} for Casco only  */
	public static AxiomaFields[] getCascoOnly(){
		AxiomaFields[] fields=AxiomaFields.values();
		ArrayList<AxiomaFields> returnValue=new ArrayList<AxiomaFields>(fields.length);
		for(AxiomaFields field:fields){
			if(field.getType().equals(AxiomaFields.Type.Casco) || field.getType().equals(AxiomaFields.Type.Casco_Osago)){
				returnValue.add(field);
			}
		}
		return returnValue.toArray(new AxiomaFields[]{});
	}
	
	/** get {@link AxiomaFields} for Osago only  */
	public static AxiomaFields[] getOsagoOnly(){
		AxiomaFields[] fields=AxiomaFields.values();
		ArrayList<AxiomaFields> returnValue=new ArrayList<AxiomaFields>(fields.length);
		for(AxiomaFields field:fields){
			if(field.getType().equals(AxiomaFields.Type.Osago) || field.getType().equals(AxiomaFields.Type.Casco_Osago)){
				returnValue.add(field);
			}
		}
		return returnValue.toArray(new AxiomaFields[]{});
	}

	public static String cutStringIfNeed(String valueForSet, AxiomaFields field) {
		if(valueForSet==null||field==null)return null;
		int size=(-1);
		try{
			size=Integer.parseInt(field.dbSize);
		}catch(Exception ex){
			// can't convert Size to int value 
		}
		if(size>=0){
			return StringUtils.substring(valueForSet, 0, size);
		}else{
			return valueForSet;
		}
	}
	
}
