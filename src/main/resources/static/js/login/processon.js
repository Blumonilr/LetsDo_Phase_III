var login = {
    init: function() {
        login.renderBg(),
            window.addEventListener("resize",
                function() {
                    login.renderBg()
                });
        var fullName = document.getElementById("login_fullname"),
            fun = "login.login()",
            forget_input = document.getElementById("forget_input");
        null != fullName ? (fun = "login.signup()", document.getElementById("login_fullname").addEventListener("keyup",
            function(e) {
                13 == e.keyCode && eval(fun)
            })) : null != forget_input && (fun = "login.forget()"),
        null != document.getElementById("login_email") && (document.getElementById("login_email").addEventListener("keyup",
            function(e) {
                13 == e.keyCode && eval(fun)
            }), null != document.getElementById("login_password") && document.getElementById("login_password").addEventListener("keyup",
            function(e) {
                13 == e.keyCode && eval(fun)
            })),
            $(".somemore").off("click.more").on("click.more",
                function(a) {
                    a.stopPropagation(),
                        $(".morelogo-con").toggleClass("popover")
                }),
            $(document).off("click.more").on("click.more",
                function() {
                    $(".morelogo-con").removeClass("popover")
                }),
            setTimeout(function() {
                    $(".error-tip").html("")
                },
                3e3)
    },
    showTip: function(a, b) {
        b.innerHTML = a,
            setTimeout(function() {
                    b.innerHTML = ""
                },
                3e3)
    },
    isLogining: !1,
    login: function() {
        if (!login.isLogining) {
            var a = document,
                b = a.getElementById("login_email"),
                c = a.getElementById("login_password");
            if ("" == b.value.trim()) return b.focus(),
                void login.showTip("閭涓嶈兘涓虹┖", b.nextElementSibling);
            if (!/^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/.test(b.value.trim())) return b.focus(),
                void login.showTip("閭鏍煎紡涓嶆纭�", b.nextElementSibling);
            if ("" == c.value.trim()) return c.focus(),
                void login.showTip("瀵嗙爜涓嶈兘涓虹┖", c.parentNode.querySelector(".error-tip"));
            b.nextElementSibling.innerHTML = "",
                c.parentNode.querySelector(".error-tip").innerHTML = "";
            var d = document.getElementsByClassName("logo-dot")[0];
            d.style.display = "inline-block",
                login.isLogining = !0,
                a.getElementById("login_email").value = b.value.trim(),
                a.getElementById("login_password").value = c.value.trim(),
                document.getElementById("loginform").submit()
        }
    },
    forget: function() {
        var a = document.getElementById("login_email"),
            b = document.getElementById("error-tip");
        if ("" == a.value.trim()) return a.focus(),
            void login.showTip("閭涓嶈兘涓虹┖", b);
        if (!/^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/.test(a.value.trim())) return a.focus(),
            void login.showTip("閭鏍煎紡涓嶆纭�", b);
        var c = document.getElementsByClassName("logo-dot")[0];
        c.style.display = "inline-block",
            $.ajax({
                url: "/login/forgetpwd",
                type: "post",
                data: {
                    email: a.value.trim()
                },
                success: function(b) {
                    "noexists" == b.result ? (a.nextElementSibling.innerHTML = "鍙戦€侀偖浠跺け璐�", a.focus()) : "success" == b.result && (a.nextElementSibling.innerHTML = "宸茬粡鍙戦€侀噸缃瘑鐮侀偖浠讹紝鏈夋晥鏃堕棿24灏忔椂 <a href='" + b.server + "' target='_blank'>璇锋煡鏀�</a>"),
                        a.nextElementSibling.style.display = "block",
                        c.style.display = "none",
                        login.isLogining = !1
                }
            }),
            login.weixin.loaded = !0
    },
    signup: function() {
        if (!login.isLogining) {
            var a = document,
                b = a.getElementById("login_email"),
                c = a.getElementById("login_password"),
                d = a.getElementById("login_fullname");
            if ("" == b.value.trim()) return b.focus(),
                void login.showTip("閭涓嶈兘涓虹┖", b.nextElementSibling);
            if (!/^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/.test(b.value.trim())) return b.focus(),
                void login.showTip("閭鏍煎紡涓嶆纭�", b.nextElementSibling);
            if ($.inArray($.trim(b.value), login.tempmails) >= 0) return b.focus(),
                void login.showTip("姝ょ被鍨嬮偖绠辨殏涓嶆敮鎸�", b.nextElementSibling);
            if ("" == c.value.trim()) return c.focus(),
                void login.showTip("瀵嗙爜涓嶈兘涓虹┖", c.nextElementSibling);
            if ("" == d.value.trim()) return d.focus(),
                void login.showTip("鏄电О涓嶈兘涓虹┖", d.nextElementSibling);
            if (d.value.length > 15) return d.focus(),
                void login.showTip("鏄电О鏈€澶ч暱搴︿负15浣�", d.nextElementSibling);
            a.getElementById("login_email").value = b.value.trim(),
                a.getElementById("login_password").value = c.value.trim(),
                a.getElementById("login_fullname").value = d.value.trim(),
                b.nextElementSibling.innerHTML = "",
                c.nextElementSibling.innerHTML = "",
                d.nextElementSibling.innerHTML = "";
            var e = document.getElementsByClassName("logo-dot")[0];
            e.style.display = "inline-block",
                login.isLogining = !0,
                document.getElementById("signup_form").submit()
        }
    },
    bind: function() {
        var a = $("#login_email"),
            b = $("#login_password"),
            c = "^.{6,24}$",
            d = document.getElementById("login_email"),
            e = document.getElementById("login_password");
        if ("" == $.trim(a.val()) || 0 == $.trim(a.val()).isEmail() || "" == $.trim(b.val())) {
            if ("" == $.trim(a.val()) || 0 == $.trim(a.val()).isEmail()) {
                a.focus();
                var d = document.getElementById("login_email");
                return login.showTip("閭鏍煎紡涓嶆纭�", d.nextElementSibling),
                    !1
            }
            if ("" == $.trim(b.val())) return b.focus(),
                login.showTip("璇疯緭鍏ュ瘑鐮�", e.nextElementSibling),
                !1
        } else {
            if ("" == $.trim(a.val()) || !$.trim(a.val()).isEmail() || "" == $.trim(b.val())) return a.focus(),
                login.showTip("閭鏍煎紡涓嶆纭�", d.nextElementSibling),
                !1;
            if (!$.trim(b.val()).match(c)) return b.focus(),
                login.showTip("瀵嗙爜鏍煎紡閿欒", e.nextElementSibling),
                !1;
            a.val($.trim(a.val())),
                b.val($.trim(b.val())),
                $("#signin_bind_form").submit()
        }
    },
    renderBg: function() {
        var a = document.getElementById("bg-canvas"),
            b = document.documentElement.clientWidth,
            c = document.documentElement.clientHeight;
        a.width = b,
            a.height = c;
        var d = a.getContext("2d");
        d.strokeStyle = "#f6f6f6",
            d.lineWidth = 1,
            d.beginPath(),
            d.translate(.5, .5);
        for (var e = 20; e <= c; e += 20) d.moveTo(0, e),
            d.lineTo(b, e);
        for (var e = 20; e <= b; e += 20) d.moveTo(e, 0),
            d.lineTo(e, c);
        d.closePath(),
            d.stroke()
    },
    paltform: {
        weixin: function() {
            login.weixin.init()
        },
        login: function(a) {
            if ("google" == a) {
                var b = "524693152001.apps.googleusercontent.com",
                    c = "https://accounts.google.com/o/oauth2/auth?redirect_uri=http://www.processon.com/google.jsp&response_type=token&client_id=" + b + "&scope=https://www.googleapis.com/auth/userinfo.profile+https://www.googleapis.com/auth/userinfo.email";
                window.open(c, "newwindow", "height=550, width=900, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no")
            } else if ("qq" == a) {
                var c = "https://graph.qq.com/oauth2.0/authorize?client_id=100485241&response_type=token&scope=all&redirect_uri=http://www.processon.com/qq.jsp";
                window.open(c, "newwindow", "height=550, width=900, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no")
            } else if ("sina" == a) {
                var c = "https://api.weibo.com/oauth2/authorize?client_id=355778041&response_type=code&redirect_uri=http://www.processon.com/login/sina";
                window.open(c, "newwindow", "height=550, width=900, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no")
            } else if ("mingdao" == a) {
                var d = "5967E9E0B4ADA1B9C23B1893ABAED0F",
                    c = "https://api.mingdao.com/oauth2/authorize?app_key=" + d + "&response_type=code&redirect_uri=http://www.processon.com/login/mingdao";
                window.open(c, "newwindow", "height=550, width=900, top=0, left=0, toolbar=no, menubar=no, scrollbars=no,resizable=no,location=no, status=no")
            }
        }
    },
    weixin: {
        loaded: !1,
        init: function() {
            var a = document.getElementById("weixin_dlg");
            if (a.style.display = "block", !login.weixin.loaded) {
                var b = {
                    js: ["https://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"]
                };
                bigPipe.render(b,
                    function() {
                        setTimeout(function() {
                                new WxLogin({
                                    id: "weixin_dlg",
                                    appid: "wx7b2c94939579c89c",
                                    scope: "snsapi_login",
                                    redirect_uri: "https://www.processon.com/login/weixin",
                                    state: "",
                                    style: "",
                                    href: ""
                                });
                                document.addEventListener("mousedown",
                                    function() {
                                        a.style.display = "none"
                                    }),
                                    a.addEventListener("mousedown",
                                        function(a) {
                                            a.stopPropagation()
                                        }),
                                    login.weixin.loaded = !0
                            },
                            1e3)
                    })
            }
        }
    },
    tempmails: ["mvrht.com", "34nm.com", "dingbone.com", "fudgerub.com", "lookugly.com", "smellfear.com", "tempinbox.com", "yopmail.com", "yopmail.fr", "yopmail.net", "cool.fr.nf", "jetable.fr.nf", "nospam.ze.tc", "nomail.xl.cx", "mega.zik.dj", "speed.1s.fr", "courriel.fr.nf", "moncourrier.fr.nf", "monemail.fr.nf", "monmail.fr.nf", "chacuo.net", "027168.com", "www.bccto.me", "mail.bccto.me", "sharklasers.com", "guerrillamail.info", "grr.la", "guerrillamail.biz", "guerrillamail.com", "guerrillamail.de", "guerrillamail.net", "guerrillamail.org", "guerrillamailblock.com", "pokemail.net", "spam4.me", "nowmymail.com", "mailcatch.com", "incognitomail.org", "cd.mintemail.com", "spamgourmet.com", "e4ward.com", "mailinator.com", "spamfree24.org", "mt2015.com", "mailnesia.com", "tempemail.net", "maildrop.cc"]
};
login.init();