<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta http-equiv="refresh" content="${(updateFrequency)!"5"}; URL=${(url)!"http://localhost:8080/observer"}">
    <title>Observer Result</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h1>All result</h1>
        <br>
        <h3>Observer parameter</h3>
        <p>URL: ${userUrl}</p>
        <p>Key words: ${keysW}</p>
        <p>Update frequency: ${(updateFrequency)} second</p>
        <#if items??>
            <#list items as item>
                <h3><a href="${item.url}">${item.title}</a></h3>
                <br>
            </#list>
        </#if>
    </div>
</body>
</html>
