<#import "main.ftlh" as main />
<@main.renderWith styles=["https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"]>
    <div class="row">
        <div class="col-4">
            <h1>Search Engine</h1>
            <form id="search-form" action="/search" method="post">
                <input type="text" name="searchText">
                <button>Search</button>
            </form>
        </div>
        <div class="col-2">
            <h5>Second exercise</h5>
            <h5><a href="/observer">Observer</a></h5>
            <h5>Third exercise</h5>
            <h5><a href="/daily-planner">Daily planner</a></h5>
        </div>
        <div class="col-3">
            <#if weather??>
                <h5>Weather in Bishkek</h5>
                <p>Weather: ${weather.weatherText}</p>
                <p>Temperature: ${(temp)!"--not found--"} C</p>
            </#if>
        </div>
        <div class="col-3">
            <h5>Exchange Rates</h5>
            <p>USD: ${(dollar)!"--not found--"} som</p>
            <p>EUR: ${(euro)!"--not found--"} som</p>
        </div>
    </div>
    <div class="row">
        <div class="col-8">
            <h1>Google</h1>
            <#if listItem??>
                <#list listItem as item>
                    <a href="${item.url}"><h2>${item.title}</h2></a>
                    <p>${item.text}</p>
                </#list>
            </#if>
            <br>
            <h1>Yandex</h1>
            <#if listItem1??>
                <#list listItem1 as item>
                    <a href="${item.url}"><h2>${item.title}</h2></a>
                    <p>${item.text}</p>
                </#list>
            </#if>
            <br>
            <h1>Bing</h1>
            <#if listItem2??>
                <#list listItem2 as item>
                    <a href="${item.url}"><h2>${item.title}</h2></a>
                    <p>${item.text}</p>
                </#list>
            </#if>
        </div>
        <div class="col-4">
            <h3>News</h3>
            <#if news??>
                <#list news as news>
                    <a href="${(news.url)!"#"}"><h6>${(news.title)!"--not found--"}</h6></a>
                    <p>${(news.text)!"--not found--"}</p>
                </#list>
            </#if>
        </div>
    </div>
</@main.renderWith>


