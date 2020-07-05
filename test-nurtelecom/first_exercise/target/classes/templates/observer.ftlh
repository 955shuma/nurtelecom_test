<#import "main.ftlh" as main />
<@main.renderWith styles=["https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"]>
    <h1>Observer</h1>
    <form id="observer-form" action="/observer-search" method="get">
        <p><label for="url">Input URL</label></p>
        <p><input type="text" id="url" name="url" placeholder="http://diesel.elcat.kg/index.php?showforum=283"></p>
        <p><label for="keyWords">Input key words</label></p>
        <p><input type="text" id="keyWords" name="keyWords" placeholder="срочно, lexus, rx330, gx470"></p>
        <p><label for="updateFrequency">Input update frequency (second)</label></p>
        <p><input type="text" id="updateFrequency" name="updateFrequency" placeholder="5"></p>
        <p><button>Search</button></p>
    </form>
    <br>
    <#if items??>
        <#list items as item>
            <h6><a href="${item.url}">${item.title}</a></h6>
            <br>
        </#list>
    </#if>
</@main.renderWith>