
$(document).ready(function (){
	if( $( '#editor' ).length )	{
		CKEDITOR.config.toolbarGroups = [
			{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
			{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
			{ name: 'forms', groups: [ 'forms' ] },
			{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
			{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
			{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
			{ name: 'links', groups: [ 'links' ] },
			{ name: 'insert', groups: [ 'insert' ] },
			{ name: 'styles', groups: [ 'styles' ] },
			{ name: 'colors', groups: [ 'colors' ] },
			{ name: 'tools', groups: [ 'tools' ] },
			{ name: 'others', groups: [ 'others' ] },
			{ name: 'about', groups: [ 'about' ] } 
		];
		CKEDITOR.config.removeButtons = 'Save,Templates,NewPage,Preview,Print,Find,Replace,SelectAll,Scayt,Form,Checkbox,Radio,TextField,Textarea,Select,ImageButton,Button,HiddenField,Subscript,Superscript,Language,BidiLtr,BidiRtl,About,Styles,Format,Font,FontSize,Flash,Smiley,SpecialChar,PageBreak,Iframe,TextColor,BGColor,Maximize,ShowBlocks,Blockquote,CreateDiv,Outdent,Indent,BulletedList,NumberedList,JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock,HorizontalRule';
		CKEDITOR.config.height = $(document).height()-70;
		
		CKEDITOR.replace( 'editor', {
			on: {
		        instanceReady: function( ev ) {
		        	var blockTags = ['div','h1','h2','h3','h4','h5','h6','p','pre','ul','li','p','img'];
		            
		            for (var i=0; i<blockTags.length; i++) {
						this.dataProcessor.writer.setRules( blockTags[i], {
			                indent: false,
			                breakBeforeOpen: true,
			                breakAfterOpen: false,
			                breakBeforeClose: false,
			                breakAfterClose: true
			            });
					}
		        }
		    }
		} );
		
	  	$('html, body').animate({
		    scrollTop: $( '#editor' ).offset().top
		}, 1000);
	}
});
