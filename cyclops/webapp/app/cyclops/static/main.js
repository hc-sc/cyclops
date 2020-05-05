var timeout  = undefined;
var interval = 1000;

//Helper function to send post data to FASTAPI
function postJson(url, data, callback) {
    return $.ajax({
        type:       'POST',
        url:         url,
        contentType: "application/json",
        dataType:    'json',
        data:        JSON.stringify (data),
        success:     callback 
    });
  }

function table_section(option){
        option           = option           || {};
        option.container = option.container || '';
        option.title     = option.title     || "Title";
        option.data      = option.data      || [];
        option.header    = option.header    || [];
        option.actions   = option.actions   || [];
        option.update    = option.update    || function(){console.log('updating', option.container);};

        var self = this;

        

        this.getData = function(){
            return option.data;
        }
        this.update  = function(propagate=true){
            $(option.container).html('');
            $(option.container).json2html([option.data], transform.section);
            if (propagate)
                option.update();
        }
        this.addElement = function(element){
            option.data.push(element);
            self.update();
        }
        this.addElements = function(elements){
            elements.forEach(element => {
                self.addElement(element);
            });
        }
        this.__populate_row__   = function(o){
                
            var results = [{"<>":"td","class":"","html":[
                {"<>":"div","class":"input-group input-group-sm","html":function(o){
                    var temp = [
                        {"<>":"div","class":"input-group-prepend","html":[
                            {"<>":"div","class":"input-group-text border-0 bg-transparent "+option.header[0].field,"html":[
                                {"<>":"input","type":"checkbox"}
                            ]}
                        ]},
                        {"<>":option.header[0].type,
                            "type":"text",
                            "class":"form-control",
                            "value":function(o){
                                return o[option.header[0].field];
                            },
                            "onkeyup": function(e){
                                    e.obj[option.header[0].field] = $(e.event.target).val();
                                    option.update();
                                },
                            "html":function(o){
                                return o[option.header[0].field];
                            }
                        },
                        {"<>":"div","class":"input-group-append ","html":[
                                    {"<>":"button",
                                    "class":option.header[0].iclass ,
                                    "type":"button",
                                    "html":[
                                        {"<>":"i","class": option.header[0].i }
                                    ]}
                        ], "onclick": function(e){
                            for (let index = option.data.length -1 ; index >= 0; index--) {
                                const element = option.data[index];
                                if (element == e.obj)
                                    option.data.splice(index, 1);
                            }
                            option.header[0].callback(e.obj);
                            self.update();
                        }}
                    ];
                    if (!option.header[0].editable) {
                        temp[1]['disabled'] = "";
                        temp[1].class    =  "form-control border-0 bg-transparent";
                    }
                    else {
                        temp[1].class    =  "form-control "
                    }

                    return $.json2html(o, temp);
                }}
            ]}];

            option.header.forEach(element => {
                if (element.type == 'checkbox')
                {
                    var tmp = {"<>":"td","class":"","html":[
                        {"<>":"div","class":"input-group-text border-0 bg-transparent "+ element.field,"html": function(o){
                            var checkbox = {"<>":"input","type":"checkbox", "onchange":function(e){
                                e.obj[element.field]=e.event.target.checked;
                                option.update();
                            }};
                            if (o[element.field])
                                checkbox['checked'] = "";
                            if (!element.editable)
                                checkbox['disabled'] = "";
                            return $.json2html(o, checkbox);
                        }}
                    ]};
                    results.push(tmp);
                }
            });
            return $.json2html(o, results);
        };
        this.__populate_header__= function(o){
            var tmp = [
                {"<>":"th","class":"small font-weight-bold","html":[
                    {"<>":"input","type":"checkbox","class":"mx-2", "onclick":function(e){
                        // if (e.obj.editable) {
                            //Update all the column to the same checkbox state
                            $(e.event.currentTarget).closest('table')
                            .find('.' + e.obj.field + '  input:checkbox:not(:disabled)')
                            .not(this)
                            .prop('checked', e.event.currentTarget.checked);
                            //Propage the change event to all the modified checkboxes
                            $(e.event.currentTarget).closest('table')
                            .find('.' + e.obj.field + '  input:checkbox:not(:disabled)')
                            .not(this).change();
                        // }
                    }},  
                    {"<>":"span", "html": "${label}"}
                ]}
            ];

            return $.json2html(option.header, tmp);
        }
        this.__populate_actions__=function(o){
            var tmp = {"<>":"button","class":"${class}","type":"button","html":"${label}", "onclick":function(e){
                var checkboxes = $(e.event.currentTarget).parent().parent().find('.'+option.header[0].field + ' input:checkbox');
                var elements   = [];
                var modified   = false;
                for (let index = checkboxes.length - 1; index >= 0; index--) {
                    const element = checkboxes[index];
                    if ($(element).prop('checked'))
                    {
                        modified = true;
                        elements.push(option.data[index]);
                        if (e.obj.modify_data)
                            option.data.splice(index, 1);
                    } 
                }
                e.obj.callback(elements);
                if (modified)
                    self.update();
            }};
            
            return $.json2html(option.actions, tmp);
        }


        var transform = {
            "section": {"<>":"div","class":"card shadow-sm my-2","html":[
                        {"<>":"div","class":option.card_header_class,"html":option.title},
                        {"<>":"div","class":"card-body p-0","html":[
                            {"<>":"table","class":"table table-sm table-striped ","html":[
                                {"<>":"thead","html":[
                                    {"<>":"tr","class":"","html":self.__populate_header__}
                                ]},
                                {"<>":"tbody","html":function(o){
                                    return $.json2html(o, transform.row);
                                }}
                            ]},
                            {"<>":"div", "html":function(o){
                                return self.__populate_actions__(o);
                            }}
                        ]}]}, 
            "row": {"<>":"tr","html":self.__populate_row__}

        };
        this.update(false);
        return this;
}

function check_empty(member) {
    return function(o) {
        return (o[member])? o[member] : '&lt;non-specified&gt;';
    }
}

function selected_product(product_proposal, option){
    option           = option           || {};
    option.container = option.container || '';
    
    var transform = {
        'card': {"<>":"div","class":"card shadow-sm my-2","html":[
            {"<>":"div","class":"card-header","html":"Selected Product"},
            {"<>":"div","class":"card-body","html":[
                {"<>":"div","class":"card-text input-group-prepend","html":[
                    {"<>":"span","class":"pre_label text-secondary","html":"Name: "},
                    {"<>":"span","class":" text-success","html":check_empty('product_name')},
                ]},
                {"<>":"div","class":"card-text input-group-prepend small",   "html":[
                    {"<>":"span","class":"pre_label text-secondary","html":"Company: "},
                    {"<>":"span","class":" text-dark","html":check_empty('company_name')},
                ]},
                {"<>":"div","class":"card-text input-group-prepend small",  "html":[
                    {"<>":"span","class":"pre_label text-secondary","html":"NPN: "},
                    {"<>":"span","class":" text-info","html":check_empty('npn')},
                ]},
                {"<>":"div","class":"card-text input-group-prepend small",  "html":[
                    {"<>":"span","class":" pre_label text-secondary","html":"ID: "},
                    {"<>":"span","class":" text-info","html":check_empty('lnhpd_id')},
                ]}
                ]}
            ]}
    }
    
    $(option.container).html('');
    $(option.container).json2html(product_proposal, transform.card);
   
    
    return this; 
}



//Functions For Report
function display_images(options) {
    options           = options           || {};
    options.container = options.container || '#table_container';
    options.data      = options.data      || [];
    options.replace   = options.replace   && true;

    var transform = {"<>":"a", "href":"image?img=${value}", "html":[
            {"<>":"img","src":"image?img=${value}","class":"img-thumbnail", "style":"width:150px;"}
    ]};

    
    if (options.replace)
        $(options.container).html('');
    
    $(options.container).json2html(options.data, transform);
    
    $(options.container).click(function(event) {

        event       = event        || window.event
        var target  = event.target || event.srcElement;
        var link    = target.src ? target.parentNode : target;
        var opt = { index: link, event: event };
        var links   = $(options.container + ' a');
        
        blueimp.Gallery(links, opt);
    });
}
function display_product(options) {
    options           = options           || {};
    options.container = options.container || '#table_container';
    options.data      = options.data      || [];
    options.replace   = options.replace   && true;


    var transform = [
            {"<>":"div","class":"input-group-prepend","html":[
                {"<>":"span","class":"pre_label text-secondary","html":"Name: "},
                {"<>":"span","class":" text-success","html":check_empty('product_name')}
                ]},
            {"<>":"div","class":"input-group-prepend ","html":[
                {"<>":"span","class":"pre_label text-secondary","html":"Company: "},
                {"<>":"span","class":" text-dark","html":check_empty('company_name')}
                ]},
            {"<>":"div","class":"card-text input-group-prepend small","html":[
                {"<>":"span","class":"pre_label text-secondary","html":"NPN: "},
                {"<>":"span","class":" text-info","html":check_empty('npn')}
                ]},
            {"<>":"div","class":"card-text input-group-prepend small","html":[
                {"<>":"span","class":" pre_label text-secondary","html":"ID: "},
                {"<>":"span","class":" text-info","html":check_empty('lnhpd_id')}
                ]}
    ];
    if (options.replace)
        $(options.container).html('');
    $(options.container).json2html(options.data, transform);
}
function display_table(options) {
    
    options           = options           || {};
    options.header    = options.header    || [];
    options.hclass    = options.hclass    || 'table-warning';
    options.container = options.container || '#table_container';
    options.data      = options.data      || [];
    options.replace   = options.replace   && true;
    options.caption   = options.caption   || '';
    
    function __populate_row__(o){
        var _transform = []
        options.header.forEach(element => {
            var tmp = {"<>":"td", "class":"", "html":""};
            if (element.type == 'checkbox') 
                tmp.html = function(_){
                    return (_[element.field])? '✓' : '';
                };
            else 
                tmp.html = '${' + element.field + '}';
            tmp.class = element.class;
            _transform.push(tmp);
        });
        return $.json2html(o, _transform);
    }
    
    var transform = {
        "table"    :[ 
            {"<>":"p", "class":"my-2 font-weight-bold", 'html':options.caption},
            {"<>":"table","class":"table table-sm mb-4 table-striped ","html":[
                {"<>":"thead","html":[
                    {"<>":"tr","class":options.hclass,"html":function(o){
                        return $.json2html(options.header, transform.th)
                    }}
                ]},
                {"<>":"tbody","html": function(o) {
                    return $.json2html(o, transform.row);
                }}
        ]}],
        'checkmark': {"<>":"i", "class":"fas fa-check"},
        "th"       : {"<>":"th","scope":"col","html":"${label}", "class":"${class}"},
        "row"      : {"<>":"tr","class":" ", "html": __populate_row__}
    };
    
    if (options.replace)
        $(options.container).html('');
    if (options.data.length > 0)
        $(options.container).json2html([options.data], transform.table);
}


function ingredient_stats(report_data){
    results = {
        'total'   :  0,
        'missing' :  0,
        'found'   :  0,
        'matched' :  0,
        'medicinal': 0,
        'pdl'      : 0,
        'cdsa'     : 0
    }
    if (report_data.product_proposal.medical_data_diff) 
    {
        medical_data_diff = report_data.product_proposal.medical_data_diff;
        
        results.found    = medical_data_diff.found.length;
        results.missing  = medical_data_diff.missing.length;
        results.matched  = medical_data_diff.matched.length;
        results.total    = results.found + results.matched;

        medical_data_diff.found.forEach(element => {
            if (element._medicinal)
                results.medicinal +=1;
            if (element._pdl)
                results.pdl       +=1;
            if (element._cdsa)
                results._cdsa     +=1;
        });
        medical_data_diff.matched.forEach(element => {
            if (element._medicinal)
                results.medicinal +=1;
            if (element._pdl)
                results.pdl       +=1;
            if (element._cdsa)
                results._cdsa     +=1;
        });

    }
    return results;
}
function claim_stats(report_data){
    results = {
        'total'   :  0,
        'missing' :  0,
        'found'   :  0,
        'matched' :  0,
        'compliant': 0
    }
    if (report_data.product_proposal.claim_data_diff) 
    {
        claim_data_diff = report_data.product_proposal.claim_data_diff;
        
        results.found    = claim_data_diff.found.length;
        results.missing  = claim_data_diff.missing.length;
        results.matched  = claim_data_diff.matched.length;
        results.total    = results.found + results.matched;

        claim_data_diff.found.forEach(element => {
            if (element._compliant)
                results.compliant +=1;
        });
        claim_data_diff.matched.forEach(element => {
            if (element._compliant)
                results.compliant +=1;
        });

    }
    return results;
}
function flags_stats(report_data){
    
    var schedule_a = [];
    var injectable = [];
    var medicinal  = [];
    var cdsa       = [];
    var pdl        = [];
    var non_compliant  = [];

    if (report_data.product_proposal.warnings) {
        warnings = report_data.product_proposal.warnings;
        warnings.forEach(element => {
            if (element._schedule_a)
                schedule_a.push(element.ocr_word)
            if (element._injectable)
                injectable.push(element.ocr_word)
        });
    }
    if (report_data.product_proposal.medical_data_diff) 
    {
        medical_data_diff = report_data.product_proposal.medical_data_diff;

        medical_data_diff.found.forEach(element => {
            if (element._medicinal)
                medicinal.push(element.ingredient_name);
            if (element._pdl)
                pdl.push(element.ingredient_name);
            if (element._cdsa)
                cdsa.push(element.ingredient_name);
        });
        medical_data_diff.matched.forEach(element => {
            if (element._medicinal)
                medicinal.push(element.ingredient_name);
            if (element._pdl)
                pdl.push(element.ingredient_name);
            if (element._cdsa)
                cdsa.push(element.ingredient_name);
        });
    }
    if (report_data.product_proposal.claim_data_diff) 
    {
        claim_data_diff = report_data.product_proposal.claim_data_diff;

        claim_data_diff.found.forEach(element => {
            if (!element._compliant)
                non_compliant.push(element.purpose)
        });
        claim_data_diff.matched.forEach(element => {
            if (!element._compliant)
                non_compliant.push(element.purpose)
        });
    }
    var warnings = []
    if (schedule_a.length) {
        warnings.push({
            'remark':'Schedule A', 
            "description":schedule_a.toString()
        })
    }
    if (injectable.length) {
        warnings.push({
            'remark': 'Injectable',
            'description': injectable.toString()
        })
    }
    if (medicinal.length) {
        warnings.push({
            'remark': 'Medicinal',
            'description': medicinal.length + ' ingredient(s)'
        })
    }
    if (cdsa.length) {
        warnings.push({
            'remark': 'CDSA',
            'description': cdsa.length + ' ingredient(s)'
        })
    }
    if (pdl.length) {
        warnings.push({
            'remark': 'PDL',
            'description': pdl.length + ' ingredient(s)'
        })
    }
    if (non_compliant.length) {
        warnings.push({
            'remark': 'Non-compliant',
            'description': non_compliant.length + ' claim(s)'
        })
    }
    return warnings;
}
