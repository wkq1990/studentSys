<!DOCTYPE html>
<!--suppress ALL -->
<html lang="zh_cn">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width" name="viewport">
    <title>考试</title>
    <link href="${staticServePath}/static/css/css.css?${staticResourceVersion}" rel="stylesheet">
    <link href="${staticServePath}/static/css/base.css?${staticResourceVersion}" rel="stylesheet">
    <script src="${staticServePath}/static/js/lib/jquery-3.1.0.min.js?${staticResourceVersion}"></script>
    <script src="${staticServePath}/static/js/lib/icheck.min.js?${staticResourceVersion}"></script>
    <script src="${staticServePath}/static/js/lib/jquery.transit.js?${staticResourceVersion}"></script>
</head>
<body>
<div class="wrapper cs-wrapper" id="wrapper">
    <div class="big-container">
        <div class="tip" id="submitTip" aria-label="0">
        </div>
    </div>
    <div id="survey_progress" class="progress">
        <div class="progress-bar" role="progressbar"
             style="width: 0;height:20px;" id="min-progress">
            0
        </div>

    <#--
            <p>开始时间<font id="start-time"></font>·结束时间<font id="end-time"></font>·现在时间<font id="now"></font></p>
    -->

    </div>
    <div class="count" id="count">倒计时：<font id="count-down"></font></div>
    <div class="tip" id="postTip"></div>
    <div class="container">
        <div class="survey_title">
            <h1>${questionnaire.testQuestionnaireTitle}</h1>
        <#if questionnaire.testQuestionnaireMessage??>
            <h4 class="survey_summary">
                <span style="color: red">试卷提示:</span>
            ${questionnaire.testQuestionnaireMessage?html}
            </h4>
        </#if>
        </div>
        <form method="get" id="questionnaire${questionnaire.id}">
        <#assign index = 0>
        <#assign bigNumber = ["一","二","三","四","五","六","七","八","九","十"]>
        <#assign xx = ['A','B','C','D','E','F','G']>
            <ul class="subject_list">
            <#list types as type>
                <li class="subject_big">
                    <h3>${type.typeName}</h3>
                </li>
                <#list questionMap["${type.id}"] as question>
                    <li class="subject"
                        id="${questionnaire.id}S${question.id}"
                        aria-label="0" data-label='0'>
                        <#assign index++>
                        <h4 class="subject_title">${index}、${question.testQuestionTitle}
                        </h4>
                        <#if (question.testQuestionContent?eval)?size gt 0>
                            <#if type.id == 1>
                                <#list question.testQuestionContent?eval as node>
                                    <label class="subject_option">
                                        <input class="iCheck" id="${question.id}S${node_index}" type="radio"
                                               name="${question.id}" value="${node_index}">
                                    ${xx[node_index]}、${node}
                                    </label>
                                </#list>
                            <#else>
                                <#list question.testQuestionContent?eval as node>
                                    <label class="subject_option">
                                        <input class="iCheck" id="${question.id}S${node_index}" type="checkbox"
                                               name="${question.id}" value="${node?index}">
                                    ${xx[node_index]}、${node}
                                    </label>
                                </#list>
                            </#if>

                        <#else>
                            <textarea class="comment" rows="10" tabindex="4"
                                      placeholder="答案" id="${question.id}S0"></textarea>
                        </#if>
                    </li>
                </#list>
            </#list>
            </ul>
        </form>
        <div style="text-align: right">
            <button class="submit" type="submit" onclick="postReply()">提交</button>
        </div>
    </div>

</div>
<div class="whole-container">
    <div class="tip" id="wholeTip" aria-label="0">
    </div>
</div>
<script type="text/javascript"
        src="${staticServePath}/static/js/lib/jquery.cookie.js?${staticResourceVersion}"></script>
<script type="text/javascript" src="${staticServePath}/static/js/common.js?${staticResourceVersion}"></script>
<script>

    var progressFlag;
    var proccer = 0;
    var max = ${questionSize};
    var answers = JSON.parse('${answers}');
    $(document).ready(function () {
        $('input').iCheck({
            checkboxClass: 'iradio_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
        });

        var t = 0;
        $(document).find('li.subject').each(function (index, doc) {
            var d = $(this);
            setTimeout(function () {
                d.addClass('a-move-in-left');
                d.css("opacity", "1");
            }, t);
            t += 100;
        });

        //时间控制
        var start = ${questionnaire.testQuestionnaireStartTime};
        var end = ${questionnaire.testQuestionnaireEndTime};
        progressFlag = ${questionnaire.id};
        getMyDate(new Date(${questionnaire.testQuestionnaireStartTime})) + "   " + getMyDate(${questionnaire.testQuestionnaireEndTime});
        $('#start-time').text(getMyDate(start));
        $('#end-time').text(getMyDate(end));
        $('#now').text(getMyDate(new Date().getTime()));
        var time = ${questionnaire.testQuestionnaireEndTime?number}-new Date().getTime();
        formatDuring(time);
        countDown();
        $('#count').transition({opacity: 1, top: 0}, 1000);

        if (check()) {
            //读取内容并初始化进度
            readAnswers();
            max = ${questionSize};

            $('input').on('ifChecked', addValue);
            $('input').on('ifUnchecked', removeValue);
            $('textarea').on('input propertychange', addText);
        }
        function readAnswers() {
            console.log('read');
            var x = 0;
            $('#questionnaire${questionnaire.id}').find('input,textarea').each(function (index, dom) {
                var ans = answers[$(dom).attr('id')];
                if (ans == 'checked'){
                    $(dom).iCheck('check');
                    var parent = $(dom).parent().parent().parent();
                    var data = parseInt(parent.attr('data-label'));
                    if (data + 1 == 1) {
                        changeProgress(parseFloat((++proccer / max * 100)).toFixed(2) + "%");
                    }
                    parent.attr('data-label', data + 1);
                }
                else if (ans != null) {
                    $(dom).val(ans);
                    var parent = $(dom).parent();
                    var data = parseInt(parent.attr('data-label'));
                    changeProgress(parseFloat((++proccer / max * 100)).toFixed(2) + "%");
                    parent.attr('data-label', data + 1);
                }
                else
                    return true;
                x++;
                console.log(ans);
            });
            return x;
        }

        function addText() {
            var val = $(this).val();
            var parent = $(this).parent();
            if (val != null && val != ''){
                answers[$(this).attr('id')] = $(this).val();
                var data = parseInt(parent.attr('data-label'));
                parent.attr('data-label', data + 1);
                if (data + 1 == 1) {
                    changeProgress(parseFloat((++proccer / max * 100)).toFixed(2) + "%");
                }
            }

            else{
                delete answers[$(this).attr('id')];
                var parent = $(this).parent();
                var data = parseInt(parent.attr('data-label'));
                parent.attr('data-label', data - 1);
                if (data - 1 <= 0) {
                    changeProgress(parseFloat((--proccer / max * 100)).toFixed(2) + "%");
                }
            }

        }

        function addValue() {
            answers[$(this).attr('id')] = "checked";
            var parent = $(this).parent().parent().parent();
            var data = parseInt(parent.attr('data-label'));
            parent.attr('data-label', data + 1);
            if (data + 1 == 1) {
                changeProgress(parseFloat((++proccer / max * 100)).toFixed(2) + "%");
            }
        }

        function removeValue() {
            delete answers[$(this).attr('id')];
            var parent = $(this).parent().parent().parent();
            var data = parseInt(parent.attr('data-label'));
            parent.attr('data-label', data - 1);
            if (data - 1 <= 0) {
                changeProgress(parseFloat((--proccer / max * 100)).toFixed(2) + "%");
            }
        }

        setTimeout(cacheAnswers, 30000);
        function cacheAnswers() {
            var ans = JSON.stringify(answers);
            var json = {
                'tqcId': '${questionnaire.testQuestionnaireClassId}'
                , 'questionnaireId':${questionnaire.id}
                , 'studentId':${student.id}
                , 'answers': ans
            };
            $.ajax({
                        url: "${staticServePath}/test/cacheAnswer",
                        dataType: 'json',
                        type: 'post',
                        data: json,
                        success: function (data, status) {
                            if (data.state == 'success') {
                                setTimeout(cacheAnswers, 30000);
                            }
                            else
                                Util.showTip($('#wholeTip'), data.msg, 'alert alert-danger');
                        }, error: function () {

                        }
                    }
            );
        }

        var minutes;
        var mm;
        var hours;
        var days;

        function countDown() {
            var now = new Date().getTime();
            $("#count-down").text(formatDuring(end - now));
            if (days <= 0 && hours <= 0 && minutes <= 0 && mm <= 0) {
                Util.showTip($('#submitTip'), "你已超时，将不能再提交");
                $("#count-down").text(0);
                return;
            }
            setTimeout(countDown, 100);
        }

        function formatDuring(mss) {
            days = parseInt(mss / (1000 * 60 * 60 * 24));
            hours = parseInt((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            minutes = parseInt((mss % (1000 * 60 * 60)) / (1000 * 60));
            //minutes = parseInt(mss / (1000 * 60));
            var seconds = parseInt((mss % (1000 * 60)) / 1000);
            mm = parseInt(mss % 1000);
            var mmmin = get3zf(mm);
            return getzf(hours) + ":" + getzf(minutes) + ":"
                    + getzf(seconds);
        }

        //获得年月日      得到日期oTime
        function getMyDate(str) {
            var oDate = new Date(str),
                    oYear = oDate.getFullYear(),
                    oMonth = oDate.getMonth() + 1,
                    oDay = oDate.getDate(),
                    oHour = oDate.getHours(),
                    oMin = oDate.getMinutes(),
                    oSen = oDate.getSeconds(),
                    oTime = oYear + '/' + getzf(oMonth) + '/' + getzf(oDay) + ' ' + getzf(oHour) + ':' + getzf(oMin) + ':' + getzf(oSen);//最后拼接时间
            return oTime;
        }

        //补0操作
        function getzf(num) {
            if (parseInt(num) < 10) {
                num = '0' + num;
            }
            return num;
        }

        function get3zf(num) {
            if (num < 10)
                num = '00' + num;
            else if (num < 100)
                num = '0' + num;
            return num;
        }

        function changeProgress(t) {
            $('#min-progress').text(t);
            $('#min-progress').css("width", t);
            $.cookie("" + progressFlag, proccer, {expires: 1});
        }
        function check() {

            return true;
        }
    });
    var score = 0;
    function validateComment(data) {
        return data.length <= 200;
    }
    function postReply() {
        if (proccer < max) {
            Util.showTip($('#submitTip'), '你尚未完成调查表，不能提交！', 'alert alert-warning');
            return;
        }
        var mode;
        var btn;
        if (!validateComment($("#${questionnaire.id}comment").val())) {

            return;
        }
        mode = {
            "questionnaireResult.id_user": "${student.name}",
            "questionnaireResult.id_questionnaire": "${questionnaire.id}",
            "questionnaireResult.comment": $("#${questionnaire.id}comment").val()
        };
        var reply = new Array();
        var i = 0;
        score = 0;
        $("#questionnaire${questionnaire.id}").find('input:radio,input:checkbox').each(function (index, domEle) {
            if ($(this).prop("checked") == true) {
                var id = $(this).attr("id");
                var index = id.lastIndexOf("T");
                var front = id.substr(0, index);
                var behind = id.substr(index, id.length - 1);
                var rp = $.parseJSON("{\"" + front + "\":\"" + behind + "\"}");
                reply[i++] = rp;
                score += Number($(this).val());

            }
        });
        reply[i++] = $.parseJSON("{\"score\":\"" + score + "\"}");
        mode["questionnaireResult.questions_reply"] = JSON.stringify(reply);

        $.ajax({
            url: "${staticServePath}/surveys/postQresult",
            type: "post",
            data: mode,
            success: function (data, textstatus) {
                if (data.state == 'success') {
                    Util.showTip($('#submitTip'), data.msg, 'alert alert-success');
                    setTimeout(function () {
                        window.location.href = "${staticServePath}/";
                    }, 1000);
                }
                if (data.state == 'error') {
                    Util.showTip($('#submitTip'), data.msg, 'alert alert-success');
                }
            }, error: function () {
                Util.showTip($('#submitTip'), '提交失败，服务器错误', 'alert alert-success');
            },
            complete: function () {

            }
        });
    }
</script>

</body>
</html>