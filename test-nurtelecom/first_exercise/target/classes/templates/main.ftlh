<#macro renderWith title="Welcome to search engine!" scripts=[] styles=["https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"]>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>${title}</title>
    <#list styles as styleSource>
        <link rel="stylesheet" href="${styleSource}">
    </#list>
</head>
<body>
<div class="container">
    <#nested>
</div>
</body>
</html>
</#macro>