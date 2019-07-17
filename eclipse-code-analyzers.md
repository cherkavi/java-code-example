-------------------------------
## Checkstyle plugin
* download Checkstyle plugin for eclipse ( install it as archive plugin )
* eclipse->project->( right click )->checkstyle->activate plugin
* eclipse->project->( right click )->properties->Checkstyle->Local Check configuration->New
	Type - External Configuration file
	Location - need to set the file from Sonar Permanent link
	Name - type the name ( "Sonar checkstyle" )
	return to tab "Main" - nearly with "Local Check configuration"
	"Simple - use the following check configuration for all files" - select "Sonar checkstyle"
* Eclipse -> Show view "Checkstyle"
	
-------------------------------
## Findbugs plugin
* install plugin: http://findbugs.cs.umd.edu/eclipse		
* load rules from sonar: eclipse->Windows->Preferences->Java->-Findbugs->Filter Files->Include->add
* Eclipse->Open Perspective->FindBugs

-------------------------------
## PMD plugin
* install plugin: http://pmd.sourceforge.net/eclipse
( need to choose the "PMD for Eclipse 3" )
* load rules from sonar: Eclipse->Project->(right click)->Properties->PMD->
	- Enable PMD (check)
	- Use the ruleset configured in a project file: ( Browse to Sonar PMD XML file )
* project->(right click)->PMD->check project with pmd
	

-------------------------------
[PMD, Checkstyle, Findbugs](http://doc.petalslink.com/display/petalsview/PMD,+CheckStyle+and+FindBugs)
[Findbugs](http://www.vogella.de/articles/Findbugs/article.html)
