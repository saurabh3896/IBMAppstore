<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/upload/message" var="messageUploadUrl"/>
<c:url value="/upload/file" var="fileUploadUrl"/>

<html>

    <head>

	  <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>IBM AppStore</title>

    <meta name="description" content="Circle Hover Effects with CSS Transitions" />
    <meta name="keywords" content="circle, border-radius, hover, css3, transition, image, thumbnail, effect, 3d" />
    <meta name="author" content="Codrops" />

    <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/fancy.css"/>'/>
    <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/jquery-ui-1.8.16.custom.css"/>'/>
	  <!--<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/style.css"/>'/>-->
	  <link rel="shortcut icon" href='<c:url value="/resources/img/favicon.ico"/>'/>
	  <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/style_boxes.css"/>'/>
    <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/demo.css"/>'/>
    <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/buttons.css"/>'/>
    <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/ribbon.css"/>'/>
    <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/list.css"/>'/>
    <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/dang.css"/>'/>
    <link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/namebox.css"/>'/>
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

    $(function(){
      init();
    });

    function init(){
      $('input:button').button();
      $('#submit').button();

      $('#uploadForm').submit(function(event){

        event.preventDefault();

        $.postJSON('${messageUploadUrl}', {
          owner: $('#owner').val(),
          description: $('#description').val(),
          filename: getFilelist()
        },
        function(result){
          if(result.success === true){
            dialog('Success', 'Files have been uploaded!');
          } else {
            dialog('Failure', 'Unable to upload files!');
          }
        });
      });

      $('#reset').click(function(){
        clearForm();
        dialog('Success', 'Fields have been cleared!');
      });

      $('#upload').fileupload({
        dataType: 'json',
        done: function(e, data){
          $.each(data.result, function(index, file){
            $('body').data('filelist').push(file);
            $('#filename').append(formatFileDisplay(file));
            var fSExt = new Array('Bytes', 'KB', 'MB', 'GB');
            fSize = file.size;
            var i = 0;
            while(fSize > 900){
              fSize /= 1024;
              i++;
            }
            $('#replaceme span').text("File name : " + file.name + ", File size : " + (Math.round(fSize*100)/100) + ' ' + fSExt[i]);
            //$('#attach').empty().append('Add another file');
          });
        }
      });

      $('#accumulator__').click(function(){
        $('#switch_input').val('accumulator');
        $("#upload").trigger('click');
      });

      $('#codelist__').click(function(){
        $('#switch_input').val('codelist');
        $("#upload").trigger('click');
      });

      $('#splitter__').click(function(){
        $('#switch_input').val('xmlsplitter');
        $("#upload").trigger('click');
      });

      $('#downgrade__').click(function(){
        $('#switch_input').val('mapversiondowngrade');
        $("#upload").trigger('click');
      });

      $('#ddf__').click(function(){
        $('#switch_input').val('ddf');
        $("#upload").trigger('click');
      });

      $('#codelist_creator__').click(function(){
        $('#switch_input').val('codelistcreator');
        $("#upload").trigger('click');
      });

      $("#attach").click(function(){
        $("#upload").trigger('click');
      });

      $("#go").click(function(){
        $("#submit").trigger('click');
      });

      $('body').data('filelist', new Array());

    }

    function getFilelist(){
      var files = $('body').data('filelist');
      var filenames = '';
      for(var i = 0;i < files.length;i < i++) {
        var suffix = (i === files.length - 1) ? '' : ',';
        filenames += files[i].name + suffix;
      }
      return filenames;
    }

    function dialog(title, text){
      $('#msgbox').text(text);
      $('#msgbox').dialog({
        title: title,
        modal: true,
        buttons: {"Ok": function(){
          //<a href="<c:url value='/download/internal' />">Download This File (located inside project)</a>
          $(this).dialog("close");}
        }
      });
    }

    function formatFileDisplay(file) {
  		var size = '<span style="font-style:italic">'+(file.size/1000).toFixed(2)+'K</span>';
  		return file.name + ' ('+ size +')<br/>';
  	}

    function clearForm(){
      $('#owner').val('');
      $('#description').val('');
      $('#filename').empty();
      $('body').data('filelist', new Array());
    }

	</script>

  </head>

  <body>

    <div id="marquee">
			<div><span>Welcome to IBM AppStore</span></div>
			<div aria-hidden="true"><span>Welcome to IBM AppStore</span></div>
		</div>

    <h1 data-shadow='Welcome,'${message}>Welcome, ${message}</h1>

    <div>
      <a href="<c:url value="http://localhost:8080/spring-security-ldap-embedded/logout" />" class="logout_button">Log Out !</a>
    </div>

		<div id="go" class="checkBox" style="margin-left:150px;margin-top:50px; width:140px">
  			Upload File
  			<svg width="140" height="65" viewBox="0 0 140 65" xmlns="http://www.w3.org/2000/svg">
    			<rect x="11" class="button" width="128.8" height="63.9"/>
    			<rect x="0" y="22.5" class="box" width="20" height="20"/>
    			<polyline class="checkMark" points="4.5,32.6 8.7,36.8 16.5,29.1"/>
  			</svg>
		</div>

    <div class="list-type1">
      <ol>
        <li><a href="#"><div id="replaceme"><span>*File description here*</span></div></a></li>
      </ol>
    </div>

		<div id="reset" class="checkBox" style="margin-left:1050px;margin-top:-110px; width:140px">
  			Reset File
  			<svg width="140" height="65" viewBox="0 0 140 65" xmlns="http://www.w3.org/2000/svg">
    			<rect x="10" class="button" width="128.8" height="63.9"/>
    			<rect x="0" y="22.5" class="box" width="20" height="20"/>
    			<polyline class="checkMark" points="4.5,32.6 8.7,36.8 16.5,29.1"/>
  			</svg>
		</div>


    <form id='uploadForm'>

      <center>
        <input type='submit' value="Let's Go !" id='submit' style='margin:75px; display:none' />
      </center>

      <input id="upload" type="file" name="file" data-url="${fileUploadUrl}" multiple style="opacity:0; filter:alpha(opacity: 0);">

      <label for='switch_input'></label><input type='text' id='switch_input' name='getowner' value='default' style='display:none'/>

      <div id='msgbox' title='' style='display:none'></div>

    </form>


    <div class="container">

    <section class="main">

      <div id='msgbox' title='' style='display:none'></div>

			<ul class="ch-grid">

        <li>

					<div class="ch-item">
            <div class="ch-info">
              <div class="ch-info-front ch-img-1" style="background-color:#009FD4;">
                <h3>accumulator report</h3>
                <h4>Input: map/mxl/zip</h4>
                <h4>Output: xml</h4>
              </div>

							<div class="ch-info-back">
                <h3 style="color:#fff; font-size:15px; margin:-2px">Generates reports which contain Accumulators and its corrsponding actions on respective fields</h3>
                	<form id='uploadForm'>
                  	<center>
                  		<input id='accumulator__' type='button' value="Choose File" style = 'margin:75px;' />
                  	</center>
                	</form>
              </div>
            </div>
					</div>

        </li>

					<li>
						<div class="ch-item">
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:#AA8F00;">
									<h3>codelist report</h3>
									<h4>Input: map/mxl/zip</h4>
									<h4>Output: xml</h4>
								</div>

								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Generates reports which contain Code lists names and its corresponding statements on respective fields</h3>
									<form id='uploadForm'>
                                    	<center>
                                    		<input id='codelist__' type='button' value="Choose File" id='submit' style = 'margin:75px;' />
                                    	</center>
                                	</form>
								</div>
							</div>
						</div>
					</li>

					<li>
						<div class="ch-item">
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:#E63022;">
									<h3>Error 20001 report</h3>
									<h4>Input: map/mxl/zip</h4>
									<h4>Output: xml</h4>
								</div>

								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Generates reports which contain all fields which will throw the 20001 compilation error</h3>
									<form id='uploadForm'>
                                    	<center>
                                    		<input id='c' type='button' value="Choose File" id='submit' style = 'margin:75px;' />
                                    	</center>
                                	</form>
								</div>
							</div>
						</div>
					</li>

					<li>
						<div class="ch-item">
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:#28A228;">
									<h3>XML splitter</h3>
									<h4>Input: dat/zip</h4>
									<h4>Output: xml</h4>
								</div>

								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Split the xml file into multiples files for each root element</h3>
									<form id='uploadForm'>
                                    	<center>
                                    		<input id='splitter__' type='button' value="Choose File" id='submit' style = 'margin:75px;' />
                                    	</center>
                                	</form>
								</div>
							</div>
						</div>
					</li>

					<li>
						<div class="ch-item">
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:#4B6A88;">
									<h3>map version downgrade</h3>
									<h4>Input: map/mxl/zip</h4>
									<h4>Output: xml</h4>
								</div>

								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Downgrade map version from 0.x to version 5.x</h3>
									<form id='uploadForm'>
                                    	<center>
                                    		<input id='downgrade__' type='button' value="Choose File" id='submit' style = 'margin:75px;' />
                                    	</center>
                                	</form>
								</div>
							</div>
						</div>
					</li>

					<li>
						<div class="ch-item">
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:#D400D4;">
									<h3>positional ddf generator</h3>
									<h4>Input: xls/zip</h4>
									<h4>Output: ddf</h4>
								</div>

								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Generate DDF file for new map from excel sheet (xls)</h3>
									<form id='uploadForm'>
                                    	<center>
                                    		<input id='ddf__' type='button' value="Choose File" id='submit' style = 'margin:75px;' />
                                    	</center>
                                	</form>
								</div>
							</div>
						</div>
					</li>

					<li>
						<div class="ch-item">
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:#D50000;">
									<h3>tech mrs generator</h3>
									<h4>Input: map/mxl</h4>
									<h4>Output: xlsx</h4>
								</div>

								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">Utility to generate technical MRS</h3>
									<form id='uploadForm'>
                                    	<center>
                                    		<input id='g' type='button' value="Choose File" id='submit' style = 'margin:75px;' />
                                    	</center>
                                	</form>
								</div>
							</div>
						</div>
					</li>

					<li>
						<div class="ch-item">
							<div class="ch-info">
								<div class="ch-info-front ch-img-1" style="background-color:#B381B3;">
									<h3>codelist creator</h3>
									<h4>Input: xml/xls</h4>
									<h4>Output: xml/xls</h4>
								</div>

								<div class="ch-info-back">
									<h3 style="color:#fff; font-size:15px; margin:-2px">convert XML to xls format and vice-versa</h3>
									<form id='uploadForm'>
                                    	<center>
                                    		<input id='codelist_creator__' type='button' value="Choose File" id='submit' style = 'margin:75px;' />
                                    	</center>
                                	</form>
								</div>
							</div>
						</div>
					</li>

				</ul>

            </section>

        </div>

    </body>

</html>
