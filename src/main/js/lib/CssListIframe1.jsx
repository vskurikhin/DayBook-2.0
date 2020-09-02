const FILES = [
    '/css/theme.css',
    '/css/primeicons.css',
    '/css/main.362f8fd8.chunk.css'
]

export const loadCssListIframe1 = () => {
    var iframe1 = document.getElementById('iframe1'); // getElementsByTagName("iframe")[0].contentWindow;
    var head = iframe1.contentWindow.document.getElementsByTagName("head")[0];
    FILES.forEach(function (item, i, arr) {
        var cssLink = document.createElement("link", {href: item, rel: 'stylesheet', type: 'text/css'});
        cssLink.href = item;
        cssLink.rel = "stylesheet";
        cssLink.type = "text/css";
        head.appendChild(cssLink);
    });
}
