$(function() {
		init();
	});
	
	function init() {
		$('input:button').button();
		$('#submit').button();
		
		$('#uploadForm').submit(function(event) {
			event.preventDefault();	
			
			$.postJSON('${messageUploadUrl}', {
						owner: $('#owner').val(),
						description: $('#description').val(),
						filename: getFilelist()
					},
					function(result) {
						if (result.success === true) {
							dialog('Success', 'Files have been uploaded!');
						} else {
							dialog('Failure', 'Unable to upload files!');
						}
			});
		});
		
		$('#reset').click(function() {
			clearForm();
			dialog('Success', 'Fields have been cleared!');
		});
		
		$('#upload').fileupload({
	        dataType: 'json',
	        done: function (e, data) {
	            $.each(data.result, function (index, file) {
	                $('body').data('filelist').push(file);
	                $('#filename').append(formatFileDisplay(file));
	                $('#attach').empty().append('Add another file');
	            });
	        }
	    });
		
		// Technique borrowed from http://stackoverflow.com/questions/1944267/how-to-change-the-button-text-of-input-type-file
		// http://stackoverflow.com/questions/210643/in-javascript-can-i-make-a-click-event-fire-programmatically-for-a-file-input
		$("#attach").click(function () {
		    $("#upload").trigger('click');
		});
		
		$('body').data('filelist', new Array());
	}
	
	function formatFileDisplay(file) {
		var size = '<span style="font-style:italic">'+(file.size/1000).toFixed(2)+'K</span>';
		return file.name + ' ('+ size +')<br/>';
	}
	
	function getFilelist() {
		var files = $('body').data('filelist');
		var filenames = '';
		for (var i=0; i<files.length; i<i++) {
			var suffix = (i===files.length-1) ? '' : ',';
			filenames += files[i].name + suffix;
		}
		return filenames;
	}
	
	function dialog(title, text) {
		$('#msgbox').text(text);
		$('#msgbox').dialog( 
				{	title: title,
					modal: true,
					buttons: {"Ok": function()  {
						$(this).dialog("close");} 
					}
				});
	}
	
	function clearForm() {
		$('#owner').val('');
		$('#description').val('');
		$('#filename').empty();
		$('#attach').empty().append('Add a file');
		$('body').data('filelist', new Array());
	}