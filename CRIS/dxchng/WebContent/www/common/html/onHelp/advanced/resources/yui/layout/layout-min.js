!function(){function i(t,e){h.isObject(t)&&!t.tagName&&(e=t,t=null),e={element:t=(t=h.isString(t)&&n.get(t)?n.get(t):t)||document.body,attributes:e||{}},i.superclass.constructor.call(this,e.element,e.attributes)}var t,n=YAHOO.util.Dom,s=YAHOO.util.Event,h=YAHOO.lang;i._instances={},i.getLayoutById=function(t){return i._instances[t]||!1},YAHOO.extend(i,YAHOO.util.Element,{browser:((t=YAHOO.env.ua).standardsMode=!1,t.secure=!1,t),_units:null,_rendered:null,_zIndex:null,_sizes:null,_setBodySize:function(t){var e=0,i=0;t=!1!==t,this._isBody?(e=n.getClientHeight(),i=n.getClientWidth()):(e=parseInt(this.getStyle("height"),10),i=parseInt(this.getStyle("width"),10),isNaN(i)&&(i=this.get("element").clientWidth),isNaN(e)&&(e=this.get("element").clientHeight)),this.get("minWidth")&&i<this.get("minWidth")&&(i=this.get("minWidth")),this.get("minHeight")&&e<this.get("minHeight")&&(e=this.get("minHeight")),t&&(i<0&&(i=0),n.setStyle(this._doc,"height",(e=e<0?0:e)+"px"),n.setStyle(this._doc,"width",i+"px")),this._sizes.doc={h:e,w:i},this._setSides(t)},_setSides:function(t){var e=this._units.top?this._units.top.get("height"):0,i=this._units.bottom?this._units.bottom.get("height"):0,s=this._sizes.doc.h,h=this._sizes.doc.w;t=!1!==t,this._sizes.top={h:e,w:this._units.top?h:0,t:0},this._sizes.bottom={h:i,w:this._units.bottom?h:0};i=s-(e+i);this._sizes.left={h:i,w:this._units.left?this._units.left.get("width"):0},this._sizes.right={h:i,w:this._units.right?this._units.right.get("width"):0,l:this._units.right?h-this._units.right.get("width"):0,t:this._units.top?this._sizes.top.h:0},this._units.right&&t&&(this._units.right.set("top",this._sizes.right.t),this._units.right._collapsing||this._units.right.set("left",this._sizes.right.l),this._units.right.set("height",this._sizes.right.h,!0)),this._units.left&&(this._sizes.left.l=0,this._units.top?this._sizes.left.t=this._sizes.top.h:this._sizes.left.t=0,t&&(this._units.left.set("top",this._sizes.left.t),this._units.left.set("height",this._sizes.left.h,!0),this._units.left.set("left",0))),this._units.bottom&&(this._sizes.bottom.t=this._sizes.top.h+this._sizes.left.h,t&&(this._units.bottom.set("top",this._sizes.bottom.t),this._units.bottom.set("width",this._sizes.bottom.w,!0))),this._units.top&&t&&this._units.top.set("width",this._sizes.top.w,!0),this._setCenter(t)},_setCenter:function(t){var e=this._sizes.left.h,i=this._sizes.doc.w-(this._sizes.left.w+this._sizes.right.w);(t=!1!==t)&&(this._units.center.set("height",e,!0),this._units.center.set("width",i,!0),this._units.center.set("top",this._sizes.top.h),this._units.center.set("left",this._sizes.left.w)),this._sizes.center={h:e,w:i,t:this._sizes.top.h,l:this._sizes.left.w}},getSizes:function(){return this._sizes},getUnitById:function(t){return YAHOO.widget.LayoutUnit.getLayoutUnitById(t)},getUnitByPosition:function(t){return!!t&&(t=t.toLowerCase(),this._units[t]||!1)},removeUnit:function(t){delete this._units[t.get("position")],this.resize()},addUnit:function(t){if(!t.position)return!1;if(this._units[t.position])return!1;var e=null,i=null;t.id&&n.get(t.id)&&(e=n.get(t.id),delete t.id),t.element&&(e=t.element);var i=document.createElement("div"),s=n.generateId();i.id=s,e=e||document.createElement("div"),n.addClass(e,"yui-layout-wrap"),this.browser.ie&&!this.browser.standardsMode&&(i.style.zoom=1,e.style.zoom=1),i.firstChild?i.insertBefore(e,i.firstChild):i.appendChild(e),this._doc.appendChild(i);var h=!1,o=!1;t.height&&(h=parseInt(t.height,10)),t.width&&(o=parseInt(t.width,10));s={};YAHOO.lang.augmentObject(s,t),s.parent=this,s.wrap=e,s.height=h,s.width=o;s=new YAHOO.widget.LayoutUnit(i,s);return s.on("heightChange",this.resize,{unit:s},this),s.on("widthChange",this.resize,{unit:s},this),s.on("gutterChange",this.resize,{unit:s},this),this._units[t.position]=s,this._rendered&&this.resize(),s},_createUnits:function(){var t,e=this.get("units");for(t in e)h.hasOwnProperty(e,t)&&this.addUnit(e[t])},resize:function(t,e){var i=t;return i&&i.prevValue&&i.newValue&&i.prevValue==i.newValue&&e&&e.unit&&(e.unit.get("animate")||(t=!1)),(t=!1!==t)&&(!1===this.fireEvent("beforeResize")&&(t=!1),this.browser.ie&&(this._isBody?(n.removeClass(document.documentElement,"yui-layout"),n.addClass(document.documentElement,"yui-layout")):(this.removeClass("yui-layout"),this.addClass("yui-layout")))),this._setBodySize(t),t&&this.fireEvent("resize",{target:this,sizes:this._sizes,event:i}),this},_setupBodyElements:function(){this._doc=n.get("layout-doc"),this._doc||(this._doc=document.createElement("div"),this._doc.id="layout-doc",document.body.firstChild?document.body.insertBefore(this._doc,document.body.firstChild):document.body.appendChild(this._doc)),this._createUnits(),this._setBodySize(),s.on(window,"resize",this.resize,this,!0),n.addClass(this._doc,"yui-layout-doc")},_setupElements:function(){this._doc=this.getElementsByClassName("yui-layout-doc")[0],this._doc||(this._doc=document.createElement("div"),this.get("element").appendChild(this._doc)),this._createUnits(),this._setBodySize(),n.addClass(this._doc,"yui-layout-doc")},_isBody:null,_doc:null,init:function(t,e){this._zIndex=0,i.superclass.init.call(this,t,e),this.get("parent")&&(this._zIndex=this.get("parent")._zIndex+10),this._sizes={},this._units={};h.isString(t)||(t=n.generateId(t)),i._instances[t]=this},render:function(){this._stamp();var t=this.get("element");return t&&t.tagName&&"body"==t.tagName.toLowerCase()?(this._isBody=!0,n.addClass(document.body,"yui-layout"),n.hasClass(document.body,"yui-skin-sam")&&(n.addClass(document.documentElement,"yui-skin-sam"),n.removeClass(document.body,"yui-skin-sam")),this._setupBodyElements()):(this._isBody=!1,this.addClass("yui-layout"),this._setupElements()),this.resize(),this._rendered=!0,this.fireEvent("render"),this},_stamp:function(){"CSS1Compat"==document.compatMode&&(this.browser.standardsMode=!0),0===window.location.href.toLowerCase().indexOf("https")&&(n.addClass(document.documentElement,"secure"),this.browser.secure=!0)},initAttributes:function(t){i.superclass.initAttributes.call(this,t),this.setAttributeConfig("units",{writeOnce:!0,validator:YAHOO.lang.isArray,value:t.units||[]}),this.setAttributeConfig("minHeight",{value:t.minHeight||!1,validator:YAHOO.lang.isNumber}),this.setAttributeConfig("minWidth",{value:t.minWidth||!1,validator:YAHOO.lang.isNumber}),this.setAttributeConfig("height",{value:t.height||!1,validator:YAHOO.lang.isNumber,method:function(t){this.setStyle("height",(t=t<0?0:t)+"px")}}),this.setAttributeConfig("width",{value:t.width||!1,validator:YAHOO.lang.isNumber,method:function(t){this.setStyle("width",(t=t<0?0:t)+"px")}}),this.setAttributeConfig("parent",{writeOnce:!0,value:t.parent||!1,method:function(t){t&&t.on("resize",this.resize,this,!0)}})},destroy:function(){var t,e,i=this.get("parent");for(t in i&&i.removeListener("resize",this.resize,this,!0),s.removeListener(window,"resize",this.resize,this,!0),this.unsubscribeAll(),this._units)h.hasOwnProperty(this._units,t)&&this._units[t]&&this._units[t].destroy(!0);for(e in s.purgeElement(this.get("element"),!0),this.get("parentNode").removeChild(this.get("element")),delete YAHOO.widget.Layout._instances[this.get("id")],this)h.hasOwnProperty(this,e)&&(this[e]=null,delete this[e]);i&&i.resize()},toString:function(){return this.get?"Layout #"+this.get("id"):"Layout"}}),YAHOO.widget.Layout=i}(),function(){function s(t,e){e={element:t,attributes:e||{}},s.superclass.constructor.call(this,e.element,e.attributes)}var n=YAHOO.util.Dom,e=YAHOO.util.Selector,h=YAHOO.util.Event,o=YAHOO.lang;s._instances={},s.getLayoutUnitById=function(t){return s._instances[t]||!1},YAHOO.extend(s,YAHOO.util.Element,{STR_CLOSE:"Click to close this pane.",STR_COLLAPSE:"Click to collapse this pane.",STR_EXPAND:"Click to expand this pane.",LOADING_CLASSNAME:"loading",browser:null,_sizes:null,_anim:null,_resize:null,_clip:null,_gutter:null,header:null,body:null,footer:null,_collapsed:null,_collapsing:null,_lastWidth:null,_lastHeight:null,_lastTop:null,_lastLeft:null,_lastScroll:null,_lastCenterScroll:null,_lastScrollTop:null,resize:function(t){var e,i,s,h;return!1===this.fireEvent("beforeResize")||this._collapsing&&!0!==t||(e=this.get("scroll"),this.set("scroll",!1),i=this._getBoxSize(this.header),s=this._getBoxSize(this.footer),t=(h=[this.get("height"),this.get("width")])[0]-i[0]-s[0]-(this._gutter.top+this._gutter.bottom),h=h[1]-(this._gutter.left+this._gutter.right),t=t+(i[0]+s[0]),h=h,this._collapsed&&!this._collapsing?(this._setHeight(this._clip,t),this._setWidth(this._clip,h),n.setStyle(this._clip,"top",this.get("top")+this._gutter.top+"px"),n.setStyle(this._clip,"left",this.get("left")+this._gutter.left+"px")):(!this._collapsed||this._collapsed&&this._collapsing)&&(t=this._setHeight(this.get("wrap"),t),h=this._setWidth(this.get("wrap"),h),this._sizes.wrap.h=t,this._sizes.wrap.w=h,n.setStyle(this.get("wrap"),"top",this._gutter.top+"px"),n.setStyle(this.get("wrap"),"left",this._gutter.left+"px"),this._sizes.header.w=this._setWidth(this.header,h),this._sizes.header.h=i[0],this._sizes.footer.w=this._setWidth(this.footer,h),this._sizes.footer.h=s[0],n.setStyle(this.footer,"bottom","0px"),this._sizes.body.h=this._setHeight(this.body,t-(i[0]+s[0])),this._sizes.body.w=this._setWidth(this.body,h),n.setStyle(this.body,"top",i[0]+"px"),this.set("scroll",e),this.fireEvent("resize"))),this},_setWidth:function(t,e){var i;return t&&(e-=(i=this._getBorderSizes(t))[1]+i[3],e=this._fixQuirks(t,e,"w"),n.setStyle(t,"width",(e=e<0?0:e)+"px")),e},_setHeight:function(t,e){var i;return t&&(e-=(i=this._getBorderSizes(t))[0]+i[2],e=this._fixQuirks(t,e,"h"),n.setStyle(t,"height",(e=e<0?0:e)+"px")),e},_fixQuirks:function(t,e,i){var s=0,h=2;return"w"==i&&(s=1,h=3),this.browser.ie<8&&!this.browser.standardsMode&&(i=this._getBorderSizes(t),t=this._getBorderSizes(t.parentNode),0===i[s]&&0===i[h]?0!==t[s]&&0!==t[h]&&(e-=t[s]+t[h]):0===t[s]&&0===t[h]&&(e+=i[s]+i[h])),e},_getBoxSize:function(t){var e,i=[0,0];return t&&(this.browser.ie&&!this.browser.standardsMode&&(t.style.zoom=1),e=this._getBorderSizes(t),i[0]=t.clientHeight+(e[0]+e[2]),i[1]=t.clientWidth+(e[1]+e[3])),i},_getBorderSizes:function(t){var e=[];t=t||this.get("element"),this.browser.ie&&!this.browser.standardsMode&&(t.style.zoom=1),e[0]=parseInt(n.getStyle(t,"borderTopWidth"),10),e[1]=parseInt(n.getStyle(t,"borderRightWidth"),10),e[2]=parseInt(n.getStyle(t,"borderBottomWidth"),10),e[3]=parseInt(n.getStyle(t,"borderLeftWidth"),10);for(var i=0;i<e.length;i++)isNaN(e[i])&&(e[i]=0);return e},_createClip:function(){var t;this._clip||(this._clip=document.createElement("div"),this._clip.className="yui-layout-clip yui-layout-clip-"+this.get("position"),this._clip.innerHTML='<div class="collapse"></div>',(t=this._clip.firstChild).title=this.STR_EXPAND,h.on(t,"click",this.expand,this,!0),this.get("element").parentNode.appendChild(this._clip))},_toggleClip:function(){if(this._collapsed)n.setStyle(this._clip,"display","none");else{var t=this._getBoxSize(this.header),e=this._getBoxSize(this.footer),i=[this.get("height"),this.get("width")],s=i[0]-t[0]-e[0]-(this._gutter.top+this._gutter.bottom),h=i[1]-(this._gutter.left+this._gutter.right),o=s+(t[0]+e[0]);switch(this.get("position")){case"top":case"bottom":this._setWidth(this._clip,h),this._setHeight(this._clip,this.get("collapseSize")),n.setStyle(this._clip,"left",this._lastLeft+this._gutter.left+"px"),"bottom"==this.get("position")?n.setStyle(this._clip,"top",this._lastTop+this._lastHeight-(this.get("collapseSize")-this._gutter.top)+"px"):n.setStyle(this._clip,"top",this.get("top")+this._gutter.top+"px");break;case"left":case"right":this._setWidth(this._clip,this.get("collapseSize")),this._setHeight(this._clip,o),n.setStyle(this._clip,"top",this.get("top")+this._gutter.top+"px"),"right"==this.get("position")?n.setStyle(this._clip,"left",this._lastLeft+this._lastWidth-this.get("collapseSize")-this._gutter.left+"px"):n.setStyle(this._clip,"left",this.get("left")+this._gutter.left+"px")}n.setStyle(this._clip,"display","block"),this.setStyle("display","none")}},getSizes:function(){return this._sizes},toggle:function(){return this._collapsed?this.expand():this.collapse(),this},expand:function(){if(!this._collapsed)return this;if(!1===this.fireEvent("beforeExpand"))return this;if(this._collapsing=!0,this.setStyle("zIndex",this._zIndex),this._anim){this.setStyle("display","none");var t,e={};switch(this.get("position")){case"left":case"right":this.set("width",this._lastWidth,!0),this.setStyle("width",this._lastWidth+"px"),this.get("parent").resize(!1),t=this.get("parent").getSizes()[this.get("position")],this.set("height",t.h,!0);var i=t.l,e={left:{to:i}};"left"==this.get("position")&&(e.left.from=i-t.w,this.setStyle("left",i-t.w+"px"));break;case"top":case"bottom":this.set("height",this._lastHeight,!0),this.setStyle("height",this._lastHeight+"px"),this.get("parent").resize(!1),t=this.get("parent").getSizes()[this.get("position")],this.set("width",t.w,!0);i=t.t;e={top:{to:i}},"top"==this.get("position")&&(this.setStyle("top",i-t.h+"px"),e.top.from=i-t.h)}this._anim.attributes=e;var s=function(){this.setStyle("display","block"),this.resize(!0),this._anim.onStart.unsubscribe(s,this,!0)},h=function(){this._collapsing=!1,this.setStyle("zIndex",this._zIndex),this.set("width",this._lastWidth),this.set("height",this._lastHeight),this._collapsed=!1,this.resize(),this.set("scroll",this._lastScroll),0<this._lastScrollTop&&(this.body.scrollTop=this._lastScrollTop),this._anim.onComplete.unsubscribe(h,this,!0),this.fireEvent("expand")};this._anim.onStart.subscribe(s,this,!0),this._anim.onComplete.subscribe(h,this,!0),this._anim.animate(),this._toggleClip()}else this._collapsing=!1,this._toggleClip(),this._collapsed=!1,this._zIndex=this.getStyle("zIndex"),this.setStyle("zIndex",this.get("parent")._zIndex),this.setStyle("display","block"),this.set("width",this._lastWidth),this.set("height",this._lastHeight),this.resize(),this.set("scroll",this._lastScroll),0<this._lastScrollTop&&(this.body.scrollTop=this._lastScrollTop),this.fireEvent("expand");return this},collapse:function(){if(this._collapsed)return this;if(!1===this.fireEvent("beforeCollapse"))return this;this._clip||this._createClip(),this._collapsing=!0;var t=this.get("width"),e=this.get("height"),i={};this._lastWidth=t,this._lastHeight=e,this._lastScroll=this.get("scroll"),this._lastScrollTop=this.body.scrollTop,this.set("scroll",!1,!0),this._lastLeft=parseInt(this.get("element").style.left,10),this._lastTop=parseInt(this.get("element").style.top,10),isNaN(this._lastTop)&&(this._lastTop=0,this.set("top",0)),isNaN(this._lastLeft)&&(this._lastLeft=0,this.set("left",0)),this._zIndex=this.getStyle("zIndex"),this.setStyle("zIndex",this.get("parent")._zIndex+1);var s,h=this.get("position");switch(h){case"top":case"bottom":this.set("height",this.get("collapseSize")+(this._gutter.top+this._gutter.bottom)),i={top:{to:this.get("top")-e}},"bottom"==h&&(i.top.to=this.get("top")+e);break;case"left":case"right":this.set("width",this.get("collapseSize")+(this._gutter.left+this._gutter.right)),i={left:{to:-this._lastWidth}},"right"==h&&(i.left={to:this.get("left")+t})}return this._anim?(this._anim.attributes=i,this._anim.onComplete.subscribe(s=function(){this._collapsing=!1,this._toggleClip(),this.setStyle("zIndex",this.get("parent")._zIndex),this._collapsed=!0,this.get("parent").resize(),this._anim.onComplete.unsubscribe(s,this,!0),this.fireEvent("collapse")},this,!0),this._anim.animate()):(this._collapsing=!1,this.setStyle("display","none"),this._toggleClip(),this.setStyle("zIndex",this.get("parent")._zIndex),this.get("parent").resize(),this._collapsed=!0,this.fireEvent("collapse")),this},close:function(){return this.setStyle("display","none"),this.get("parent").removeUnit(this),this.fireEvent("close"),this._clip&&(this._clip.parentNode.removeChild(this._clip),this._clip=null),this.get("parent")},loadHandler:{success:function(t){this.body.innerHTML=t.responseText,this.resize(!0)},failure:function(t){}},dataConnection:null,_loading:!1,loadContent:function(){return!(!YAHOO.util.Connect||!this.get("dataSrc")||this._loading||this.get("dataLoaded"))&&(this._loading=!0,n.addClass(this.body,this.LOADING_CLASSNAME),this.dataConnection=YAHOO.util.Connect.asyncRequest(this.get("loadMethod"),this.get("dataSrc"),{success:function(t){this.loadHandler.success.call(this,t),this.set("dataLoaded",!0),this.dataConnection=null,n.removeClass(this.body,this.LOADING_CLASSNAME),this._loading=!1,this.fireEvent("load")},failure:function(t){this.loadHandler.failure.call(this,t),this.dataConnection=null,n.removeClass(this.body,this.LOADING_CLASSNAME),this._loading=!1,this.fireEvent("loadError",{error:t})},scope:this,timeout:this.get("dataTimeout")}),this.dataConnection)},init:function(t,e){this._gutter={left:0,right:0,top:0,bottom:0},this._sizes={wrap:{h:0,w:0},header:{h:0,w:0},body:{h:0,w:0},footer:{h:0,w:0}},s.superclass.init.call(this,t,e),this.browser=this.get("parent").browser;o.isString(t)||(t=n.generateId(t)),(s._instances[t]=this).setStyle("position","absolute"),this.addClass("yui-layout-unit"),this.addClass("yui-layout-unit-"+this.get("position"));t=this.getElementsByClassName("yui-layout-hd","div")[0];t&&(this.header=t);t=this.getElementsByClassName("yui-layout-bd","div")[0];t&&(this.body=t);t=this.getElementsByClassName("yui-layout-ft","div")[0];t&&(this.footer=t),this.on("contentChange",this.resize,this,!0),this._lastScrollTop=0,this.set("animate",this.get("animate"))},initAttributes:function(t){s.superclass.initAttributes.call(this,t),this.setAttributeConfig("wrap",{value:t.wrap||null,method:function(t){t&&(t=n.generateId(t),s._instances[t]=this)}}),this.setAttributeConfig("grids",{value:t.grids||!1}),this.setAttributeConfig("top",{value:t.top||0,validator:o.isNumber,method:function(t){this._collapsing||this.setStyle("top",t+"px")}}),this.setAttributeConfig("left",{value:t.left||0,validator:o.isNumber,method:function(t){this._collapsing||this.setStyle("left",t+"px")}}),this.setAttributeConfig("minWidth",{value:t.minWidth||!1,method:function(t){this._resize&&this._resize.set("minWidth",t)},validator:YAHOO.lang.isNumber}),this.setAttributeConfig("maxWidth",{value:t.maxWidth||!1,method:function(t){this._resize&&this._resize.set("maxWidth",t)},validator:YAHOO.lang.isNumber}),this.setAttributeConfig("minHeight",{value:t.minHeight||!1,method:function(t){this._resize&&this._resize.set("minHeight",t)},validator:YAHOO.lang.isNumber}),this.setAttributeConfig("maxHeight",{value:t.maxHeight||!1,method:function(t){this._resize&&this._resize.set("maxHeight",t)},validator:YAHOO.lang.isNumber}),this.setAttributeConfig("height",{value:t.height,validator:o.isNumber,method:function(t){this._collapsing||this.setStyle("height",(t=t<0?0:t)+"px")}}),this.setAttributeConfig("width",{value:t.width,validator:o.isNumber,method:function(t){this._collapsing||this.setStyle("width",(t=t<0?0:t)+"px")}}),this.setAttributeConfig("zIndex",{value:t.zIndex||!1,method:function(t){this.setStyle("zIndex",t)}}),this.setAttributeConfig("position",{value:t.position}),this.setAttributeConfig("gutter",{value:t.gutter||0,validator:YAHOO.lang.isString,method:function(t){t=t.split(" ");t.length&&(this._gutter.top=parseInt(t[0],10),t[1]?this._gutter.right=parseInt(t[1],10):this._gutter.right=this._gutter.top,t[2]?this._gutter.bottom=parseInt(t[2],10):this._gutter.bottom=this._gutter.top,t[3]?this._gutter.left=parseInt(t[3],10):t[1]?this._gutter.left=this._gutter.right:this._gutter.left=this._gutter.top)}}),this.setAttributeConfig("parent",{writeOnce:!0,value:t.parent||!1,method:function(t){t&&t.on("resize",this.resize,this,!0)}}),this.setAttributeConfig("collapseSize",{value:t.collapseSize||25,validator:YAHOO.lang.isNumber}),this.setAttributeConfig("duration",{value:t.duration||.5}),this.setAttributeConfig("easing",{value:t.easing||(YAHOO.util&&YAHOO.util.Easing?YAHOO.util.Easing.BounceIn:"false")}),this.setAttributeConfig("animate",{value:!1!==t.animate,validator:function(){var t=!1;return t=YAHOO.util.Anim?!0:t},method:function(t){this._anim=!!t&&new YAHOO.util.Anim(this.get("element"),{},this.get("duration"),this.get("easing"))}}),this.setAttributeConfig("header",{value:t.header||!1,method:function(t){var e;!1===t?this.header&&(n.addClass(this.body,"yui-layout-bd-nohd"),this.header.parentNode.removeChild(this.header),this.header=null):(this.header||(e=(e=this.getElementsByClassName("yui-layout-hd","div")[0])||this._createHeader(),this.header=e),(e=this.header.getElementsByTagName("h2")[0])||(e=document.createElement("h2"),this.header.appendChild(e)),e.innerHTML=t,this.body&&n.removeClass(this.body,"yui-layout-bd-nohd")),this.fireEvent("contentChange",{target:"header"})}}),this.setAttributeConfig("proxy",{writeOnce:!0,value:!1!==t.proxy}),this.setAttributeConfig("body",{value:t.body||!1,method:function(t){this.body||((e=this.getElementsByClassName("yui-layout-bd","div")[0])?this.body=e:((e=document.createElement("div")).className="yui-layout-bd",this.body=e,this.get("wrap").appendChild(e))),this.header||n.addClass(this.body,"yui-layout-bd-nohd"),n.addClass(this.body,"yui-layout-bd-noft");var e,i=null;o.isString(t)?i=n.get(t):t&&t.tagName&&(i=t),i?(e=n.generateId(i),(s._instances[e]=this).body.appendChild(i)):this.body.innerHTML=t,this._cleanGrids(),this.fireEvent("contentChange",{target:"body"})}}),this.setAttributeConfig("footer",{value:t.footer||!1,method:function(t){var e;!1===t?this.footer&&(n.addClass(this.body,"yui-layout-bd-noft"),this.footer.parentNode.removeChild(this.footer),this.footer=null):(this.footer||((e=this.getElementsByClassName("yui-layout-ft","div")[0])?this.footer=e:((e=document.createElement("div")).className="yui-layout-ft",this.footer=e,this.get("wrap").appendChild(e))),e=null,o.isString(t)?e=n.get(t):t&&t.tagName&&(e=t),e?this.footer.appendChild(e):this.footer.innerHTML=t,n.removeClass(this.body,"yui-layout-bd-noft")),this.fireEvent("contentChange",{target:"footer"})}}),this.setAttributeConfig("close",{value:t.close||!1,method:function(t){if("center"==this.get("position"))return!1;var e;!this.header&&t&&this._createHeader(),this.header&&(e=this.header?n.getElementsByClassName("close","div",this.header)[0]:null,t?(this.get("header")||this.set("header","&nbsp;"),e||((e=document.createElement("div")).className="close",this.header.appendChild(e),h.on(e,"click",this.close,this,!0)),e.title=this.STR_CLOSE):e&&e.parentNode&&(h.purgeElement(e),e.parentNode.removeChild(e)),this._configs.close.value=t,this.set("collapse",this.get("collapse")))}}),this.setAttributeConfig("collapse",{value:t.collapse||!1,method:function(t){if("center"==this.get("position"))return!1;var e;!this.header&&t&&this._createHeader(),this.header&&(e=this.header?n.getElementsByClassName("collapse","div",this.header)[0]:null,t?(this.get("header")||this.set("header","&nbsp;"),e||(e=document.createElement("div"),this.header.appendChild(e),h.on(e,"click",this.collapse,this,!0)),e.title=this.STR_COLLAPSE,e.className="collapse"+(this.get("close")?" collapse-close":"")):e&&e.parentNode&&(h.purgeElement(e),e.parentNode.removeChild(e)))}}),this.setAttributeConfig("scroll",{value:(!0===t.scroll||!1===t.scroll||null===t.scroll)&&t.scroll,method:function(t){!1!==t||this._collapsed||this.body&&0<this.body.scrollTop&&(this._lastScrollTop=this.body.scrollTop),!0===t?(this.addClass("yui-layout-scroll"),this.removeClass("yui-layout-noscroll"),0<this._lastScrollTop&&this.body&&(this.body.scrollTop=this._lastScrollTop)):!1===t?(this.removeClass("yui-layout-scroll"),this.addClass("yui-layout-noscroll")):null===t&&(this.removeClass("yui-layout-scroll"),this.removeClass("yui-layout-noscroll"))}}),this.setAttributeConfig("hover",{writeOnce:!0,value:t.hover||!1,validator:YAHOO.lang.isBoolean}),this.setAttributeConfig("useShim",{value:t.useShim||!1,validator:YAHOO.lang.isBoolean,method:function(t){this._resize&&this._resize.set("useShim",t)}}),this.setAttributeConfig("resize",{value:t.resize||!1,validator:function(t){return!(!YAHOO.util||!YAHOO.util.Resize)},method:function(t){if(t&&!this._resize){if("center"==this.get("position"))return!1;var e=!1;switch(this.get("position")){case"top":e="b";break;case"bottom":e="t";break;case"right":e="l";break;case"left":e="r"}this.setStyle("position","absolute"),e&&(this._resize=new YAHOO.util.Resize(this.get("element"),{proxy:this.get("proxy"),hover:this.get("hover"),status:!1,autoRatio:!1,handles:[e],minWidth:this.get("minWidth"),maxWidth:this.get("maxWidth"),minHeight:this.get("minHeight"),maxHeight:this.get("maxHeight"),height:this.get("height"),width:this.get("width"),setSize:!1,useShim:this.get("useShim"),wrap:!1}),this._resize._handles[e].innerHTML='<div class="yui-layout-resize-knob"></div>',this.get("proxy")&&(this._resize.getProxyEl().innerHTML='<div class="yui-layout-handle-'+e+'"></div>'),this._resize.on("startResize",function(t){var e;this._lastScroll=this.get("scroll"),this.set("scroll",!1),this.get("parent")&&(this.get("parent").fireEvent("startResize"),e=this.get("parent").getUnitByPosition("center"),this._lastCenterScroll=e.get("scroll"),e.addClass(this._resize.CSS_RESIZING),e.set("scroll",!1)),this.fireEvent("startResize")},this,!0),this._resize.on("resize",function(t){this.set("height",t.height),this.set("width",t.width)},this,!0),this._resize.on("endResize",function(t){var e;this.set("scroll",this._lastScroll),this.get("parent")&&((e=this.get("parent").getUnitByPosition("center")).set("scroll",this._lastCenterScroll),e.removeClass(this._resize.CSS_RESIZING)),this.resize(),this.fireEvent("endResize")},this,!0))}else this._resize&&this._resize.destroy()}}),this.setAttributeConfig("dataSrc",{value:t.dataSrc}),this.setAttributeConfig("loadMethod",{value:t.loadMethod||"GET",validator:YAHOO.lang.isString}),this.setAttributeConfig("dataLoaded",{value:!1,validator:YAHOO.lang.isBoolean,writeOnce:!0}),this.setAttributeConfig("dataTimeout",{value:t.dataTimeout||null,validator:YAHOO.lang.isNumber})},_cleanGrids:function(){var t;this.get("grids")&&((t=e.query("div.yui-b",this.body,!0))&&n.removeClass(t,"yui-b"),h.onAvailable("yui-main",function(){n.setStyle(e.query("#yui-main"),"margin-left","0"),n.setStyle(e.query("#yui-main"),"margin-right","0")}))},_createHeader:function(){var t=document.createElement("div");return t.className="yui-layout-hd",this.get("firstChild")?this.get("wrap").insertBefore(t,this.get("wrap").firstChild):this.get("wrap").appendChild(t),this.header=t},destroy:function(t){this._resize&&this._resize.destroy();var e,i=this.get("parent");for(e in this.setStyle("display","none"),this._clip&&(this._clip.parentNode.removeChild(this._clip),this._clip=null),t||i.removeUnit(this),i&&i.removeListener("resize",this.resize,this,!0),this.unsubscribeAll(),h.purgeElement(this.get("element"),!0),this.get("parentNode").removeChild(this.get("element")),delete YAHOO.widget.LayoutUnit._instances[this.get("id")],this)o.hasOwnProperty(this,e)&&(this[e]=null,delete this[e]);return i},toString:function(){return this.get?"LayoutUnit #"+this.get("id")+" ("+this.get("position")+")":"LayoutUnit"}}),YAHOO.widget.LayoutUnit=s}(),YAHOO.register("layout",YAHOO.widget.Layout,{version:"2.9.0",build:"2800"});