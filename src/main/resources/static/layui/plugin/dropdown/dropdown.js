/* layui_dropdown v2.1.0 by Microanswer,doc:http://test.microanswer.cn/page/dropdown.html */
layui.define(["jquery", "laytpl"], function (i) {
    "use strict";

    function e(i, t) {
        "string" == typeof i && (i = s(i)), this.$dom = i, this.option = s.extend({
            downid: String(Math.random()).split(".")[1],
            filter: i.attr("lay-filter")
        }, o, t), 20 < this.option.gap && (this.option.gap = 20), this.init()
    }

    var s = layui.jquery || layui.$, n = layui.laytpl, d = s(window.document.body), h = "a", a = {},
        r = window.MICROANSWER_DROPDOWAN || "dropdown",
        c = "<div tabindex='0' class='layui-anim layui-anim-upbit dropdown-root' " + r + "-id='{{d.downid}}' style='z-index: {{d.zIndex}}'><div class='dropdown-pointer'></div><div class='dropdown-content' style='margin: {{d.gap}}px {{d.gap}}px;background-color: {{d.backgroundColor}};min-width: {{d.minWidth}}px;min-height: {{d.minHeight}}px;max-height: {{d.maxHeight}}px;overflow: auto;'>",
        l = "</div></div>",
        p = c + "{{# layui.each(d.menus, function(index, item){ }}{{# if ('hr' === item) { }}<hr>{{# } else if (item.header) { }}{{# if (item.withLine) { }}<fieldset class=\"layui-elem-field layui-field-title menu-header\" style=\"margin-left:0;margin-bottom: 0;margin-right: 0\"><legend>{{item.header}}</legend></fieldset>{{# } else { }}<div class='menu-header' style='text-align: {{item.align||'left'}}'>{{item.header}}</div>{{# } }}{{# } else { }}<div class='menu-item'><a href='javascript:;' lay-event='{{item.event}}'>{{# if (item.layIcon){ }}<i class='layui-icon {{item.layIcon}}'></i>&nbsp;{{# } }}<span>{{item.txt}}</span></a></div>{{# } }}{{# }); }}" + l,
        o = {
            showBy: "click",
            align: "left",
            minWidth: 10,
            minHeight: 10,
            maxHeight: 300,
            zIndex: 891,
            gap: 8,
            onHide: function (i, t) {
            },
            onShow: function (i, t) {
            },
            scrollBehavior: "follow",
            backgroundColor: "#FFF",
            cssLink: "https://cdn.jsdelivr.net/gh/microanswer/layui_dropdown@2.1.0/dist/dropdown.css"
        };

    function u(i, n) {
        s(i || "[lay-" + r + "]").each(function () {
            var i = s(this), t = new Function("return " + (i.attr("lay-" + r) || "{}"))();
            i.removeAttr("lay-" + r);
            var o = i.data(r) || new e(i, s.extend({}, t, n || {}));
            i.data(r, o)
        })
    }

    layui.link(window[r + "_cssLink"] || o.cssLink, function () {
    }, r + "_css"), e.prototype.init = function () {
        var t = this;
        if (t.option.menus && 0 < t.option.menus.length) n(p).render(t.option, function (i) {
            t.$down = s(i), t.$dom.after(t.$down), t.initSize(), t.initEvent()
        }); else if (t.option.template) {
            var i;
            i = -1 === t.option.template.indexOf("#") ? "#" + t.option.template : t.option.template;
            var o = s.extend(s.extend({}, t.option), t.option.data || {});
            n(c + s(i).html() + l).render(o, function (i) {
                t.$down = s(i), t.$dom.after(t.$down), t.option.success && t.option.success(t.$down), t.initSize(), t.initEvent()
            })
        } else layui.hint().error("下拉框目前即没配置菜单项，也没配置下拉模板。[#" + (t.$dom.attr("id") || "") + ",filter=" + t.option.filter + "]")
    }, e.prototype.initSize = function () {
        this.$down.find(".dropdown-pointer").css("width", 2 * this.option.gap), this.$down.find(".dropdown-pointer").css("height", 2 * this.option.gap)
    }, e.prototype.initPosition = function () {
        var i, t, o, n, e = this.$dom.offset(), s = this.$dom.outerHeight(), d = this.$dom.outerWidth(), h = e.left,
            a = e.top - window.pageYOffset, r = this.$down.outerHeight(), c = this.$down.outerWidth();
        o = "right" === this.option.align ? (i = h + d - c + this.option.gap, -Math.min(c - 2 * this.option.gap, d) / 2) : "center" === this.option.align ? (i = h + (d - c) / 2, (c - 2 * this.option.gap) / 2) : (i = h - this.option.gap, Math.min(c - 2 * this.option.gap, d) / 2), t = s + a;
        var l = this.$down.find(".dropdown-pointer");
        n = -this.option.gap, 0 < o ? (l.css("left", o), l.css("right", "unset")) : (l.css("left", "unset"), l.css("right", -1 * o)), t + r >= window.innerHeight ? (t = a - r, n = r - this.option.gap, l.css("top", n).addClass("bottom")) : l.css("top", n).removeClass("bottom"), i + c >= window.innerWidth && (i = window.innerWidth - c + this.option.gap), this.$down.css("left", i), this.$down.css("top", t)
    }, e.prototype.show = function (i) {
        var o, t;
        this.initPosition(), this.$down.addClass("layui-show"), this.opened = !0, this.fource = i || !1, o = this, t = a[h] || [], s.each(t, function (i, t) {
            t(o)
        }), this.option.onShow && this.option.onShow(this.$dom, this.$down)
    }, e.prototype.hide = function () {
        this.fcd = !1, this.$down.removeClass("layui-show"), this.opened = !1, this.fource = !1, this.option.onHide && this.option.onHide(this.$dom, this.$down)
    }, e.prototype.hideWhenCan = function () {
        this.mic || this.fource || this.fcd || this.hide()
    }, e.prototype.toggle = function () {
        this.opened ? this.hide() : this.show()
    }, e.prototype._onScroll = function () {
        var i = this;
        "follow" === this.option.scrollBehavior ? setTimeout(function () {
            i.initPosition()
        }, 1) : this.hide()
    }, e.prototype.initEvent = function () {
        var i, t, o, n = this;
        t = function (i) {
            i !== n && n.hide()
        }, (o = a[i = h] || []).push(t), a[i] = o, n.$dom.mouseenter(function () {
            n.mic = !0, "hover" === n.option.showBy && (n.fcd = !0, n.$down.focus(), n.show())
        }), n.$dom.mouseleave(function () {
            n.mic = !1
        }), n.$down.mouseenter(function () {
            n.mic = !0, n.$down.focus()
        }), n.$down.mouseleave(function () {
            n.mic = !1
        }), "click" === n.option.showBy && n.$dom.on("click", function () {
            n.fcd = !0, n.toggle()
        }), d.on("click", function () {
            n.mic || (n.fcd = !1, n.hideWhenCan())
        }), s(window).on("scroll", function () {
            n._onScroll()
        }), n.$dom.parents().on("scroll", function () {
            n._onScroll()
        }), s(window).on("resize", function () {
            n.initPosition()
        }), n.$dom.on("blur", function () {
            n.fcd = !1, n.hideWhenCan()
        }), n.$down.on("blur", function () {
            n.fcd = !1, n.hideWhenCan()
        }), n.option.menus && s("[" + r + "-id='" + n.option.downid + "']").on("click", "a", function () {
            var i = (s(this).attr("lay-event") || "").trim();
            i ? (layui.event.call(this, r, r + "(" + n.option.filter + ")", i), n.hide()) : layui.hint().error("菜单条目[" + this.outerHTML + "]未设置event。")
        })
    }, u(), i(r, {
        suite: u, onFilter: function (i, t) {
            layui.onevent(r, r + "(" + i + ")", function (i) {
                t && t(i)
            })
        }, hide: function (i) {
            s(i).each(function () {
                var i = s(this).data(r);
                i && i.hide()
            })
        }, show: function (t, o) {
            s(t).each(function () {
                var i = s(this).data(r);
                i ? i.show(!0) : (layui.hint().error("警告：尝试在选择器【" + t + "】上进行下拉框show操作，但此选择器对应的dom并没有初始化下拉框。"), u(t, o))
            })
        }, version: "2.1.0"
    })
});