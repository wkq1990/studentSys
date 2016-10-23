<link href="${staticServePath}/static/css/css.css?${staticResourceVersion}" rel="stylesheet">
<script src="${staticServePath}/static/js/lib/icheck.min.js?${staticResourceVersion}"></script>

<div class="wrapper cs-wrapper scroll" id="wrapper">
    <div class="big-container">
        <div class="tip" id="submitTip" aria-label="0">
        </div>
    </div>
    <div class="">
        <div class="survey_title">
        </div>
        <form method="get" id="questionnaire${questionnaire.id}">
        <#assign index = 0>
        <#assign scoreSituation = testReply?eval["scoreSituation"]?eval>
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
                        data-label='0'>
                        <div class="row">
                            <div class="col-md-6 right-border">
                                <#assign index++>
                                <h3 class="subject_title">${index}、${question.testQuestionTitle}    <span class="small">分数：${scoreMap["${question.id}"]}</span>
                                </h3>
                                <#if (question.testQuestionContent?eval)?size gt 0>
                                    <#if type.id == 1>
                                        <#list question.testQuestionContent?eval as node>
                                            <label class="subject_option point_item" id="${question.id}S${node_index}"
                                                   data-label="${questionnaire.id}S${question.id}"
                                                   data-index="${node_index}">
                                                <input class="iCheck" name="${questionnaire.id}S${question.id}"
                                                       type="radio">
                                            ${xx[node_index]}、${node}
                                            </label>
                                        </#list>
                                    <#else>
                                        <#list question.testQuestionContent?eval as node>
                                            <label class="subject_option point_item" id="${question.id}S${node_index}"
                                                   data-label="${questionnaire.id}S${question.id}"
                                                   data-index="${node_index}">
                                                <input class="iCheck" name="${questionnaire.id}S${question.id}"
                                                       type="checkbox">
                                            ${xx[node_index]}、${node?html}
                                            </label>
                                        </#list>
                                    </#if>

                                <#else>
                                <#--<textarea class="comment" rows="10" tabindex="4"
                                          placeholder="答案"></textarea>-->

                                    <pre><p></p></pre>
                                </#if>
                            </div>
                            <div class="col-md-6">
                                <#assign ans = question.testQuestionShortAnswer?eval>
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label for="score${question.id}" class="control-label">得分：</label>
                                        <input class="form-control score" style="width:80px; " type="text"
                                               id="score${question.id}" data-label="${questionnaire.id}S${question.id}"
                                               value="<#if (question.testQuestionContent?eval)?size gt 0>${scoreSituation["${questionnaire.id}S${question.id}"]}</#if>"/>
                                        <label class="tip control-label" id="score${question.id}-tip" aria-label="0">
                                        </label>
                                    </div>
                                </div>
                                <br/>
                                <#if (question.testQuestionContent?eval)?size gt 0>
                                    <div class="form-group">
                                        <label class="control-label">答案：</label>
                                        <label class="text-red control-label"
                                               id="answer${question.id}"><#list ans as an>${xx[an?number]}</#list></label>
                                    </div>
                                <#else>
                                    <div class="panel panel-warning">
                                        <div class="panel-heading">
                                            答案：
                                        </div>
                                        <div class="panel-body">
                                            <pre>${question.testQuestionLongAnswer}</pre>
                                        </div>
                                    </div>
                                </#if>

                            </div>
                        </div>
                    </li>
                </#list>
            </#list>
            </ul>
        </form>
    <#--<div class="row">
        <div id="survey_progress" class="col-md-12">
            <div class="progress-bar" role="progressbar"
                 style="width: 0;height:20px;" id="min-progress">
                0
            </div>
        </div>
    </div>-->
        <div style="text-align: right">
            <button class="submit" type="submit" onclick="updateReply()">提交</button>
        </div>
    </div>
</div>
<script type="text/javascript">
    var proccer = 0;
    var max = ${questionSize};
    var reply = ${testReply};
    var answers = JSON.parse(reply['answers']);
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
        //读取内容并初始化进度
        readAnswers();
        max = ${questionSize};

        /*$('.point_item').on('ifChecked', addValue);
        $('.point_item').on('ifUnchecked', addValue);
        $('textarea').on('input propertychange', addText);*/
        $('.score').on('input onpropertychange', addProgress);
        function addProgress() {
            var x = 0;
            var score = 0;
            $('#wrapper').find('.score').each(function (index, dom) {
                var val = $(dom).val();
                var id = $(dom).attr('id');
                if (val != null && val != '') {
                    x++;
                    console.log(Validate.isNum(val));
                    if (Validate.isNum(val))
                        score += parseInt(val);
                    else
                        Util.showTip($('#'+id+'-tip'), '请输入数字！', 'bg-danger', {time: 1500});
                }
            });
            changeProgress(parseFloat((x / max * 100)).toFixed(2) + "%");
            $('#icon-span').text(score);
        }

        function readAnswers() {
            var x = 0;
            for (var key in answers) {
                var i = 0;
                var dom = $('#' + key);
                dom.find('input,p').each(function (index, dom) {
                    if ($(dom).is('p')) {
                        x++;
                        var str = Util.formatText(answers[key][0]);
                        $(dom).html(str);
                        i++;
                        return false;
                    }
                    var ind = $(dom).parent().parent().attr('data-index');
                    if ($.inArray(ind, answers[key]) >= 0) {
                        $(dom).iCheck('check');
                        x++;
                        i++;
                    }
                });
            }
            //$('input').iCheck('disable');
            addProgress();
            return x;
        }

        /*function addText() {
            var val = $(this).val();
            var parent = $(this).parent();
            if (val != null && val != '') {
                answers[parent.attr('id')] = [val];
                var data = parseInt(parent.attr('data-label'));
                parent.attr('data-label', 1);
                if (val.length > 0 && data == 0) {
                    changeProgress(parseFloat((++proccer / max * 100)).toFixed(2) + "%");
                }
            }

            else {
                var parent = $(this).parent();
                delete answers[parent.attr('id')];
                var data = parseInt(parent.attr('data-label'));
                parent.attr('data-label', 0);
                if (val.length == 0 && data == 1) {
                    changeProgress(parseFloat((--proccer / max * 100)).toFixed(2) + "%");
                }
            }

        }*/

        /*function addValue() {
            var ans = [];
            $(this).parent().find('input').each(function (index, dom) {
                if ($(dom).prop('checked') == true) {
                    ans.push(index + '');
                }
            });
            var len = 0;
            if (answers[$(this).attr('data-label')] != null) {
                len = answers[$(this).attr('data-label')].length;
            }
            answers[$(this).attr('data-label')] = ans;
            if (ans.length == 1 && len == 0) {
                changeProgress(parseFloat((++proccer / max * 100)).toFixed(2) + "%");
            }
            if (ans.length <= 0) {
                if (len == 1)
                    changeProgress(parseFloat((--proccer / max * 100)).toFixed(2) + "%");
                delete answers[$(this).attr('data-label')];
            }
        }*/


        function changeProgress(t) {
            /*$('#min-progress').text(t);*/
            $('#min-progress').css("width", t);
        }
    });
    function updateReply() {
        var count = 0;
        var scoreSituation = {};
        $('li.subject input[type=text]').each(function (index, dom) {
            var key = $(dom).attr('data-label');
            var score = parseInt($(dom).val());
            scoreSituation[key] = score;
            count += score;
        });
        $.ajax({
            url: "${staticServePath}/test/postScore/${(testReply?eval)["id"]}",
            data: {
                "scoreSituation": JSON.stringify(scoreSituation),
                "score": count
            },
            type: 'post',
            dataType: 'json',
            success: function (data, status) {
                $('#modal').modal('hide');
                if (data.state == 'success') {
                    Util.showTip($('#wholeTip'), data.msg, 'alert alert-success');
                    $('tr#${(testReply?eval)["id"]}>.score').html(count);
                    $('tr#${(testReply?eval)["id"]}>.state').html('<span class="text-success">已批改</span>');
                } else {
                    Util.showTip($('#wholeTip'), data.msg, 'alert alert-danger');
                }
            }
        });
    }

</script>