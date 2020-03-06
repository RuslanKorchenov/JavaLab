<#macro page title>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>${title}</title>

        <!-- Font Icon -->
        <link rel="stylesheet" href="/src/fonts/material-icon/css/material-design-iconic-font.min.css">

        <!-- Main css -->
        <link rel="stylesheet" href="/src/css/style.css">
    </head>
    <body>
    <#nested/>

    <script src="/src/vendor/jquery/jquery.min.js"></script>
    <script src="/src/js/main.js"></script>
    </body>
</#macro>