(function(e){function t(t){for(var r,o,c=t[0],i=t[1],s=t[2],l=0,d=[];l<c.length;l++)o=c[l],Object.prototype.hasOwnProperty.call(a,o)&&a[o]&&d.push(a[o][0]),a[o]=0;for(r in i)Object.prototype.hasOwnProperty.call(i,r)&&(e[r]=i[r]);p&&p(t);while(d.length)d.shift()();return u.push.apply(u,s||[]),n()}function n(){for(var e,t=0;t<u.length;t++){for(var n=u[t],r=!0,o=1;o<n.length;o++){var c=n[o];0!==a[c]&&(r=!1)}r&&(u.splice(t--,1),e=i(i.s=n[0]))}return e}var r={},o={app:0},a={app:0},u=[];function c(e){return i.p+"js/"+({}[e]||e)+"."+{"chunk-3bbc720e":"bc543acd","chunk-47035d4a":"07bd6927","chunk-5939bb9c":"ac81cda1","chunk-8cb7cbd4":"d0c64c52"}[e]+".js"}function i(t){if(r[t])return r[t].exports;var n=r[t]={i:t,l:!1,exports:{}};return e[t].call(n.exports,n,n.exports,i),n.l=!0,n.exports}i.e=function(e){var t=[],n={"chunk-3bbc720e":1,"chunk-47035d4a":1,"chunk-5939bb9c":1,"chunk-8cb7cbd4":1};o[e]?t.push(o[e]):0!==o[e]&&n[e]&&t.push(o[e]=new Promise((function(t,n){for(var r="css/"+({}[e]||e)+"."+{"chunk-3bbc720e":"6b7b7a45","chunk-47035d4a":"22b7f024","chunk-5939bb9c":"87635cd5","chunk-8cb7cbd4":"3a375a43"}[e]+".css",a=i.p+r,u=document.getElementsByTagName("link"),c=0;c<u.length;c++){var s=u[c],l=s.getAttribute("data-href")||s.getAttribute("href");if("stylesheet"===s.rel&&(l===r||l===a))return t()}var d=document.getElementsByTagName("style");for(c=0;c<d.length;c++){s=d[c],l=s.getAttribute("data-href");if(l===r||l===a)return t()}var p=document.createElement("link");p.rel="stylesheet",p.type="text/css",p.onload=t,p.onerror=function(t){var r=t&&t.target&&t.target.src||a,u=new Error("Loading CSS chunk "+e+" failed.\n("+r+")");u.code="CSS_CHUNK_LOAD_FAILED",u.request=r,delete o[e],p.parentNode.removeChild(p),n(u)},p.href=a;var f=document.getElementsByTagName("head")[0];f.appendChild(p)})).then((function(){o[e]=0})));var r=a[e];if(0!==r)if(r)t.push(r[2]);else{var u=new Promise((function(t,n){r=a[e]=[t,n]}));t.push(r[2]=u);var s,l=document.createElement("script");l.charset="utf-8",l.timeout=120,i.nc&&l.setAttribute("nonce",i.nc),l.src=c(e);var d=new Error;s=function(t){l.onerror=l.onload=null,clearTimeout(p);var n=a[e];if(0!==n){if(n){var r=t&&("load"===t.type?"missing":t.type),o=t&&t.target&&t.target.src;d.message="Loading chunk "+e+" failed.\n("+r+": "+o+")",d.name="ChunkLoadError",d.type=r,d.request=o,n[1](d)}a[e]=void 0}};var p=setTimeout((function(){s({type:"timeout",target:l})}),12e4);l.onerror=l.onload=s,document.head.appendChild(l)}return Promise.all(t)},i.m=e,i.c=r,i.d=function(e,t,n){i.o(e,t)||Object.defineProperty(e,t,{enumerable:!0,get:n})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,t){if(1&t&&(e=i(e)),8&t)return e;if(4&t&&"object"===typeof e&&e&&e.__esModule)return e;var n=Object.create(null);if(i.r(n),Object.defineProperty(n,"default",{enumerable:!0,value:e}),2&t&&"string"!=typeof e)for(var r in e)i.d(n,r,function(t){return e[t]}.bind(null,r));return n},i.n=function(e){var t=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(t,"a",t),t},i.o=function(e,t){return Object.prototype.hasOwnProperty.call(e,t)},i.p="/",i.oe=function(e){throw console.error(e),e};var s=window["webpackJsonp"]=window["webpackJsonp"]||[],l=s.push.bind(s);s.push=t,s=s.slice();for(var d=0;d<s.length;d++)t(s[d]);var p=l;u.push([0,"chunk-vendors"]),n()})({0:function(e,t,n){e.exports=n("56d7")},"56d7":function(e,t,n){"use strict";n.r(t);n("d3b7"),n("e623"),n("e379"),n("5dc8"),n("37e1");var r=n("2b0e"),o=n("8c4f"),a=function(){var e=this,t=e.$createElement,n=e._self._c||t;return n("div",{attrs:{id:"app"}},[n("router-view")],1)},u=[],c={name:"App",components:{}},i=c,s=n("2877"),l=Object(s["a"])(i,a,u,!1,null,null,null),d=l.exports,p=n("b970"),f=(n("157a"),n("bc3a")),h=n.n(f);n("4328");h.a.defaults.withCredentials=!1,h.a.interceptors.request.use((function(e){var t=sessionStorage.getItem("Authorization");return t&&(e.headers.Authorization=t),e}),(function(e){return Promise.reject(e)})),h.a.interceptors.response.use((function(e){return console.log("响应体:",e),b(e),e}),(function(e){if(401!==e.response.data.status)return Promise.reject(e);o["a"].push("/")}));function b(e){if(401!==e.status)return e.data;this.$router.push("/")}var m=function(){return n.e("chunk-5939bb9c").then(n.bind(null,"67f4"))},g=function(){return n.e("chunk-47035d4a").then(n.bind(null,"fdab"))},v=function(){return n.e("chunk-3bbc720e").then(n.bind(null,"903c"))},y=function(){return n.e("chunk-8cb7cbd4").then(n.bind(null,"578a"))};r["a"].use(p["a"]),r["a"].use(o["a"]),r["a"].config.productionTip=!1;var k=new o["a"]({mode:"history",routes:[{path:"/",name:"HelloWorld",component:g},{path:"/MainPage",name:"MainPage",component:m},{path:"/BackTestDetail",name:"BackTestDetail",component:v},{path:"/Login",name:"Login",component:y}]});new r["a"]({router:k,render:function(e){return e(d)}}).$mount("#app")}});
//# sourceMappingURL=app.b41c2ed9.js.map