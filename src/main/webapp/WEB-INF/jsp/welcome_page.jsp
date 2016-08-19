<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url value="/upload/message" var="messageUploadUrl"/>
<c:url value="/upload/file" var="fileUploadUrl"/>

<html lang="en">
    
    <head>
        
	<meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <title>IBM AppStore</title>
        
        <meta name="description" content="Circle Hover Effects with CSS Transitions" />
        <meta name="keywords" content="circle, border-radius, hover, css3, transition, image, thumbnail, effect, 3d" />
        <meta name="author" content="Codrops" />
        
        <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/jquery-ui-1.8.16.custom.css"/>'/>
	<!--<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/style.css"/>'/>-->
	<link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/demo.css"/>'/>
        <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/common.css"/>'/>
        <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/style7.css"/>'/>
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:300,700' rel='stylesheet' type='text/css' />
        <script type='text/javascript' src='<c:url value="/resources/js/modernizr.custom.79639.js"/>'></script>
        <script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.8.16.custom.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/util.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.ui.widget.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.iframe-transport.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.fileupload.js"/>'></script>
        
        <script type='text/javascript'>
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
	</script>
        
    </head>
    
    <body>
        
        <div class="container">
			
            <section class="main">
	
                <div id='msgbox' title='' style='display:none'></div>
                
                <span id='filename'></span><br/>
                <a href='#' id='attach'>Add a file</a><br/>
                <input id="upload" type="file" name="file" data-url="${fileUploadUrl}" multiple style="opacity: 0; filter:alpha(opacity: 0);"><br/>
			
		<ul class="ch-grid">
                    
                    <li>
			<div class="ch-item">				
                            <div class="ch-info">
                                <div class="ch-info-front ch-img-1" style="background-color:red;">
                                    <h3>accumulator report</h3>
                                    <h4>Input: map/mxl/zip</h4>
                                    <h4>Output: xml</h4>
                                </div>
				<div class="ch-info-back">
                                    <h3 style="color:#fff; font-size:15px; margin:-2px">Generates reports which contain Accumulators and its corrsponding actions on respective fields</h3>
                                    <form id='uploadForm'>
                                        <input type='button' value='Reset' id='reset' />
                                        <input type='submit' value='Upload' id='submit'/>
                                    </form>
                                </div>	
                            </div>
			</div>
                    </li>
					<li>
						<div class="ch-item">				
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:red;">
									<h3>codelist report</h3>
									<h4>Input: map/mxl/zip</h4>
									<h4>Output: xml</h4>
								</div>
								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Generates reports which contain Code lists names and its corresponding statements on respective fields</h3>
									<button style="margin:80px">Upload</button>
								</div>	
							</div>
						</div>
					</li>
					<li>
						<div class="ch-item">				
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:red;">
									<h3>Error 20001 report</h3>
									<h4>Input: map/mxl/zip</h4>
									<h4>Output: xml</h4>
								</div>
								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Generates reports which contain all fields which will throw the 20001 compilation error</h3>
									<button style="margin:80px">Upload</button>
								</div>	
							</div>
						</div>
					</li>
					<li>
						<div class="ch-item">				
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:red;">
									<h3>XML splitter</h3>
									<h4>Input: dat/zip</h4>
									<h4>Output: xml</h4>
								</div>
								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Split the xml file into multiples files for each root element</h3>
									<button style="margin:80px">Upload</button>
								</div>	
							</div>
						</div>
					</li>
					<li>
						<div class="ch-item">				
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:red;">
									<h3>map version downgrade</h3>
									<h4>Input: map/mxl/zip</h4>
									<h4>Output: xml</h4>
								</div>
								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Downgrade map version from 0.x to version 5.x</h3>
									<button style="margin:80px">Upload</button>
								</div>	
							</div>
						</div>
					</li>
					<li>
						<div class="ch-item">				
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:red;">
									<h3>positional ddf generator</h3>
									<h4>Input: xls/zip</h4>
									<h4>Output: ddf</h4>
								</div>
								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Generate DDF file for new map from excel sheet (xls)</h3>
									<button style="margin:80px">Upload</button>
								</div>	
							</div>
						</div>
					</li>
					<li>
						<div class="ch-item">				
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:red;">
									<h3>tech mrs generator</h3>
									<h4>Input: map/mxl</h4>
									<h4>Output: xlsx</h4>
								</div>
								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Utility to generate technical MRS</h3>
									<button style="margin:80px">Upload</button>
								</div>	
							</div>
						</div>
					</li>
					<li>
						<div class="ch-item">				
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:red;">
									<h3>codelist creator</h3>
									<h4>Input: xml/xls</h4>
									<h4>Output: xml/xls</h4>
								</div>
								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">convert XML to xls format and vice-versa</h3>
									<button style="margin:80px">Upload</button>
								</div>	
							</div>
						</div>
					</li>
		</ul>
				
            </section>
            
        </div>
        
    </body>
    
</html>