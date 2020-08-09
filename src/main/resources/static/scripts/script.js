$(document).ready(function () {
    $("#logoutButton").click(function (e) {
        e.preventDefault();
        $.ajax({
            type: 'POST',
            url: '/logout',
            success: function (data) {
                window.location.reload();
            },
            error: function () {
                console.log("Logout failed")
            }
        })
    })
})

$(document).ready(function () {
    $('input[type="file"]').change(function (e) {
        var fileName = e.target.files[0].name;
        $("#inputPhoto").text(fileName);
        console.log(fileName);
    });
});

$(document).ready(function (e) {
    $("#recognitionForm").submit(function (e) {
        e.preventDefault();
        var form = $(this);
        $.ajax({
            type: 'POST',
            url: '/recognize',
            data: form.serialize(),
            success: function (data, status, xhr) {
                setTimeout(location.reload.bind(location), 2500)
                var x = xhr.getResponseHeader("myResponseHeader");
                if (x === "doesNotExist") {
                    $("#errorAlert").append(data)
                    $("#errorAlert").show()
                }
                if (x === "selfRecognition") {
                    $("#selfRecognitionAlert").append(data)
                    $("#selfRecognitionAlert").show()
                }
                if (x === "successfulRecognition") {
                    $("#successAlert").append(data)
                    $("#successAlert").show()
                }
            },
            error: function (data) {
                alert("failed")
            }
        });
    });

    $("#successAlert").click(function () {
        window.location.reload();
    })

    $("#errorAlert").click(function () {
        window.location.reload();
    })

    $("#selfRecognitionAlert").click(function () {
        window.location.reload();
    })

    $("#searchRecognitions").submit(function (e) {
        e.preventDefault();
    });

    $("#searchRecognitionButton").click(function (e) {
        var form = $("#searchRecognitions");
        var users = $.ajax({
            type: 'POST',
            url: '/searchRecognitionByName',
            dataType: 'json',
            data: form.serialize(),
            success: function (data) {
                $("#recognitionResults").hide()
                $("#userdataDiv").empty()
                data.forEach(function (e) {
                    $("#recognitionResults").show()
                    console.log(e.receiverName)
                    $("#userdataDiv").append(
                        "<strong> " +
                        e.receiverName +
                        "</strong> has received a " +
                        e.badge +
                        " from " +
                        e.senderName +
                        " for " +
                        e.reason +
                        " because " +
                        e.comment +
                        "<br>" +
                        " on " + e.date +
                        "<br>"
                    )
                });
                $("#recognizedUserName").val("");
            },
            error: function (data) {
                console.log("Failed to get recognized users by name")
            }
        });
    });

    $("#dateToday").click(function (e) {
        console.log("Today")
        searchDates("today")
    })

    $("#dateYesterday").click(function (e) {
        console.log("Yesterday")
        searchDates("yesterday")
    })

    $("#dateLast7Days").click(function (e) {
        console.log("Last 7")
        searchDates("last7")
    })

    $("#dateLast30Days").click(function (e) {
        console.log("Last 30")
        searchDates("last30")
    })

    function searchDates(dateString) {
        console.log("Function called " + dateString)
        console.log(dateString)
        $.ajax({
            type: 'GET',
            url: '/searchRecognitionsByDate/' + dateString,
            success: function (data) {
                console.log("after controller");
                $("#recognitionResults").hide()
                $("#recognitionEmpty").hide()
                $("#userdataDiv").empty()
                data.forEach(function (e) {
                    $("#recognitionResults").show()
                    console.log(e.receiverName)
                    $("#userdataDiv").append(
                        "<strong> " +
                        e.receiverName +
                        "</strong> has received a " +
                        e.badge +
                        " from " +
                        e.senderName +
                        " for " +
                        e.reason +
                        " because " +
                        e.comment +
                        "<br>" +
                        " on " + e.date +
                        "<br>"
                    )
                });
            },
            error: function () {
                $("#recognitionEmpty").show()
                $("#nodataDiv").show()
                alert("No results found")
                console.log("failed to get recognized users by date");
            }
        })
    }

    $("#recognitionForm").submit(function (e) {
        e.preventDefault();
    });

    $("#recognizedUserName").autocomplete({
        source: function (request, response) {
            $.ajax({
                method: 'GET',
                url: "/autocomplete",
                data: {"pattern": $("#recognizedUserName").val()},
                success: function (data) {
                    var availableUsers = [];
                    data.forEach(function (e) {
                        availableUsers.push(e.fullName)
                    });
                    response(availableUsers);
                },
                error: function () {
                    console.log("failed to get recognized users by name");
                }
            })
        }
    })

    $("#userNameToRecognize").autocomplete({
        source: function (request, response) {
            $.ajax({
                method: 'GET',
                url: "/autocomplete",
                data: {"pattern": $("#userNameToRecognize").val()},
                success: function (data) {
                    var availableUsers = [];
                    data.forEach(function (e) {
                        availableUsers.push(e.fullName)
                    });
                    response(availableUsers);
                }
            })
        }
    })
    /*$('#reason').on('change', function () {
        if (this.value === 'Extra Miler') {
            $("#karma1").show();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Knowledge Sharing') {
            $("#karma2").show();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Mentorship') {
            $("#karma3").show();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Pat on the back') {
            $("#karma4").show();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Customer Delight') {
            $("#karma5").show();
            $("#karma4").hide();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Continuous Learning and Improvement') {
            $("#core1").show();
            $("#karma5").hide();
            $("#karma4").hide();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Contribution') {
            $("#core2").show();
            $("#core1").hide();
            $("#karma5").hide();
            $("#karma4").hide();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Diligence') {
            $("#core3").show();
            $("#core2").hide();
            $("#core1").hide();
            $("#karma5").hide();
            $("#karma4").hide();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Empathy') {
            $("#core4").show();
            $("#core3").hide();
            $("#core2").hide();
            $("#core1").hide();
            $("#karma5").hide();
            $("#karma4").hide();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Openness to Feedback and Change') {
            $("#core5").show();
            $("#core4").hide();
            $("#core3").hide();
            $("#core2").hide();
            $("#core1").hide();
            $("#karma5").hide();
            $("#karma4").hide();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else if (this.value === 'Responsible Freedom') {
            $("#core6").show();
            $("#core5").hide();
            $("#core4").hide();
            $("#core3").hide();
            $("#core2").hide();
            $("#core1").hide();
            $("#karma5").hide();
            $("#karma4").hide();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").hide();
        }
        else {
            $("#core6").hide();
            $("#core5").hide();
            $("#core4").hide();
            $("#core3").hide();
            $("#core2").hide();
            $("#core1").hide();
            $("#karma5").hide();
            $("#karma4").hide();
            $("#karma3").hide();
            $("#karma2").hide();
            $("#karma1").hide();
            $("#selectKarma").show();
        }
    });

    $("input[type='checkbox']").click(function () {
        if (this.checked) {
            var indicative = $(this).attr("data-text");
            var htmlText = '<textarea id="reasonTextArea" style="resize: both;height: 140px;width: 100%">' + indicative + '</textarea>';
            $('div .display').html(htmlText);
            $(".display").removeClass('displayStyle')
        }
    });
    $(".reason-checkbox").on('click', function () {
        comment();
    });*/
    /*
    $("#resetPasswordForm").submit(function (e) {
        e.preventDefault();
        console.log("Trying to submit password reset form")
        if ($("#passwordField").val() === $("#confirmPasswordField").val()) {
            console.log("Passwords match")
        } else {
            console.log("Passwords do not match")
        }
    })
    */

    $(".revokeRecognitionButtonClass").click(function (e) {
        var answer = confirm("Are you sure you want to revoke this recognition?");
        if (answer == true) {
            var recognitionId = $(this).closest(".recognitionIdClass").find("input[name='recognitionIdToRevoke']").val();
            $.ajax({
                method: 'PUT',
                url: "/recognitions/" + recognitionId,
                success: function (data) {
                    window.location.reload();
                }
            })
        } else {
            console.log("Will not revoke recognition")
        }
    })

});