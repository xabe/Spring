var waitDialogShown = false;
var useTimerBeforeShowWaitDialog = true;
var waitDialogTimeout = 50;
var waitDialogTimer;

function showWaitDialog() {
	// avoid attempt to show it if it is already shown
	if (!waitDialogShown) {
		statusDialog.show();
		waitDialogShown = true;
	}
}

function onRequestStart() {
	if (useTimerBeforeShowWaitDialog) {
		waitDialogTimer = setTimeout("showWaitDialog();", waitDialogTimeout);
	} else {
		showWaitDialog();
	}
}
function onRequestEnd() {
	if (waitDialogShown) {
		statusDialog.hide();
		waitDialogShown = false;
	} else if (useTimerBeforeShowWaitDialog && waitDialogTimer) {
		clearTimeout(waitDialogTimer);
	}
}

$(document).ready(function() {

	$('input#text').val("");
	$('#form\\:text').focus();

});

$("#form\\:tableData_data tr").live({
	mouseover : function() {
		$(this).removeClass("even-row");
		$(this).addClass("over");
	},
	mouseout : function() {
		$(this).removeClass("over");
		$(this).parent().find('tr.ui-datatable-odd').addClass("even-row");
	}
});
