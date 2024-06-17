
// TODO: consider passing value as json object (originValue, warningDivId)
const optionAffectMappingHandler = {
  displayWarningMessage: function(affectMappingWarningDiv, displayFlag){
    if(displayFlag){
      document.getElementById(affectMappingWarningDiv).innerHTML = '<div class="ebx_MessageContainer"><div class="ebx_IconWarning"><div class="ebx_Warning">'+affectMappingWarningMessage+'</div></div></div>';
    }else {
      document.getElementById(affectMappingWarningDiv).innerHTML = '';
    }
  },
  onIncludeColumnHeaderChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    optionAffectMappingHandler.handleRadioBoxValueChange(originColumnValue, newValue,affectMappingWarningDiv);
  },

  onIncludeComputedValueChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    optionAffectMappingHandler.handleRadioBoxValueChange(originIncludeComputedValue, newValue,affectMappingWarningDiv);
  },

  onCaseSensitiveLabelChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    optionAffectMappingHandler.handleRadioBoxValueChange(originCaseSensitiveValue, newValue,affectMappingWarningDiv);
  },

  onCellDelimiterChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    optionAffectMappingHandler.handleRadioBoxValueChange(originCellDelimiterValue, newValue,affectMappingWarningDiv);
  },

  handleRadioBoxValueChange: function(oldValue, newValue, affectMappingWarningDiv){
    const isChange = oldValue !== newValue;
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, isChange);
  },

  registerChangeEventOnFieldSeparator: function (affectMappingWarningDiv){

    const separatorDivElement = document.getElementById('separator_div');
    const inputSeparatorElement = separatorDivElement.querySelector('input[name="inputSeparator"]');
    const originSeparatorValue =separatorDivElement.querySelector("input[name='separator']:checked").value;
    const originInputValue = inputSeparatorElement.value;

    const displayWarningMessage = (fieldSeparatorValue,inputSeparatorValue ) => {
      if(!hasTemplateOrMappingDTO){
        return;
      }
      if(originSeparatorValue != fieldSeparatorValue || originInputValue != inputSeparatorValue){
        optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, true);
      }else {
        optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, false);
      }
    }
    separatorDivElement.querySelectorAll('input[type=radio]').forEach((element) => {
      element.addEventListener('change', function(){
        displayWarningMessage(this.value, inputSeparatorElement.value);

      })
    });

    separatorDivElement.querySelector('input[name="inputSeparator"]').addEventListener('change', function (){
      const fieldSeparatorValue =separatorDivElement.querySelector("input[name='separator']:checked").value;
      displayWarningMessage(fieldSeparatorValue, this.value);
    });
  },

  onFileEncodingChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    const isChange = newValue && originEncodingValue && originEncodingValue.key !== newValue.key;
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, isChange);
  },

  onCheckPrimaryKeyCheckBoxChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    const isChecked = newValue && newValue.length > 0 && newValue[0] ? 'true': 'false';
    optionAffectMappingHandler.displayWarningMessageForCheckBoxField(originExportPKLabel,isChecked,affectMappingWarningDiv);
  },
  onCheckForeignKeyCheckBoxChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    const isChecked = newValue && newValue.length > 0 && newValue[0] ? 'true': 'false';
    optionAffectMappingHandler.displayWarningMessageForCheckBoxField(originForeignKey,isChecked,affectMappingWarningDiv);
  },
  onCheckEnumerationCheckBoxChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    const isChecked = newValue && newValue.length > 0 && newValue[0] ? 'true': 'false';
    optionAffectMappingHandler.displayWarningMessageForCheckBoxField(originEnumeration,isChecked,affectMappingWarningDiv);
  },

  /**
   *
   * @param originValue format: [], [true]
   * @param newValue format: [], [true]
   * @param affectMappingWarningDiv
   */
  displayWarningMessageForCheckBoxField: function (originValue,newValue,affectMappingWarningDiv){
    const isChange = newValue != originValue;
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, isChange);
  },

  onStartLineChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, newValue !== originStartLine);
  },
  onStartColumnChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, newValue !== originStartColumn);
  },

  onDataSourceChange: function(newValue,affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, newValue !== originDataSource);
  },

  // EBX transfer table
  onTargetDataspaceChange: function (newValue, affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    const isChange = newValue.id !== originTargetDataspace.id;
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, isChange);
  },
  onTargetDatasetChange: function (newValue, affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    const isChange = newValue.key !== originTargetDataset.key;
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, isChange);
  },
  onTargetTableChange: function (newValue, affectMappingWarningDiv){
    if(!hasTemplateOrMappingDTO){
      return;
    }
    const isChange = newValue.key !== originTargetTable.key;
    optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, isChange);
  },

  registerEventValueChangeOnTableList(checkBoxSelectAllId, selectTableOptionId, affectMappingWarningDiv, elementIndex){
    document.getElementById(checkBoxSelectAllId).addEventListener('change', function (){
      if(!hasTemplateOrMappingDTO){
        return;
      }
      const checkboxElement = document.getElementById(checkBoxSelectAllId);
      const currentSelectedOption = [];
      if(checkboxElement.checked){
        const tableElement = document.getElementById(selectTableOptionId);
        if(tableElement.options) {
          Array.from(tableElement.options).forEach(op => currentSelectedOption.push(op.value));
        }
      }
      if(elementIndex){
        handleSelectTableChangeByIndex(currentSelectedOption,affectMappingWarningDiv, elementIndex );
      }else {
        handleSelectTableChange(currentSelectedOption,affectMappingWarningDiv );
      }

    });
    document.getElementById(selectTableOptionId).addEventListener('change', function (){
      if(!hasTemplateOrMappingDTO){
        return;
      }
      const tableElement = document.getElementById(selectTableOptionId);
      if(tableElement.options) {
        const currentSelectedOption = Array.from(tableElement.options).filter(op => op.selected).map(op => op.value);
        if(elementIndex){
          handleSelectTableChangeByIndex(currentSelectedOption,affectMappingWarningDiv, elementIndex );
        }else {
          handleSelectTableChange(currentSelectedOption,affectMappingWarningDiv);
        }

      }
    });

    const handleSelectTableChangeByIndex = (currentSelectedOption,affectMappingWarningDiv, elementIndex) => {
      const elementData = targetDataOptions[elementIndex-1];
      const originSelectedOption =  elementData && elementData.table ? elementData.table: [];
      const isChange = (JSON.stringify(currentSelectedOption) !== JSON.stringify(originSelectedOption));
      optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, isChange);
    };
    const handleSelectTableChange = (currentSelectedOption,affectMappingWarningDiv) => {
      const isChange = (JSON.stringify(currentSelectedOption) !== JSON.stringify(originSelectedOption));
      optionAffectMappingHandler.displayWarningMessage(affectMappingWarningDiv, isChange);
    };
  },




  registerEventOnSQLDataSourceChange: function(sqlDataSourceId, affectMappingWarningDiv){
    document.getElementById(sqlDataSourceId).addEventListener('change', function (){
      optionAffectMappingHandler.onDataSourceChange(this.value,affectMappingWarningDiv);
    })
  },
};

// Handle change for select source/target tables on dataset level
const optionAffectMappingSourceTargetDatasetHandler = {

  onSelectAllSourceTable: function(){
    if(!hasTemplateOrMappingDTO) return;
    // When select only template & not select import file, dintMappingWidget will be null
    const dintMappingWidget = document.getElementById("dintMappingWidget");
    if(!dintMappingWidget) return;

    const listInputs = dintMappingWidget.getElementsByClassName("checkboxTableMapping");
    for (let rowIndex = 0; rowIndex < listInputs.length; rowIndex++) {
      optionAffectMappingSourceTargetDatasetHandler.handleTargetTableValueChangeByRowIndex(parseInt(rowIndex, 10));
    }
  },

  onSelectSourceTable: function(newValue, rowIndex){
    if(!hasTemplateOrMappingDTO) return;
    optionAffectMappingSourceTargetDatasetHandler.handleTargetTableValueChangeByRowIndex(parseInt(rowIndex, 10));
  },

  onChangeStartAt: function(newValue, rowIndex){
    if(!hasTemplateOrMappingDTO) return;
    optionAffectMappingSourceTargetDatasetHandler.handleTargetTableValueChangeByRowIndex(parseInt(rowIndex, 10));
  },

  onTargetTableChange: function (newValue, rowIndex){
    if(!hasTemplateOrMappingDTO) return;
    optionAffectMappingSourceTargetDatasetHandler.handleTargetTableValueChangeByRowIndex(parseInt(rowIndex, 10));
  },

  handleTargetTableValueChangeByRowIndex: function(rowIndex) {

    if(!tableMappings) return;
    const listTableMappings = JSON.parse(tableMappings);
    if(!listTableMappings || listTableMappings.length == 0) return;
    const tableMapping = listTableMappings[rowIndex];
    const tableMappingWarningDivID = 'table_mapping_warning'+'_'+ rowIndex;
    const dintMappingWidget = document.getElementById("dintMappingWidget");
    if(!dintMappingWidget) return;

    const listInputs =   dintMappingWidget.getElementsByClassName("checkboxTableMapping");
    // compare is source table is checked
    if(listInputs && listInputs[rowIndex] && tableMapping){
      const inputElement = listInputs[rowIndex].getElementsByTagName('input')[0];
      const isChecked = inputElement.checked;
      const isChange = tableMapping.checked != isChecked;
      optionAffectMappingHandler.displayWarningMessage(tableMappingWarningDivID, isChange);
      if(isChange){
        return;
      }
    }
    // Compare start at row and column, each start at row include 2 input element
    const listDintMappingWidgetStartAt = document.querySelectorAll("[id='dintMappingWidgetStartAt']");
    if(listDintMappingWidgetStartAt && listDintMappingWidgetStartAt[rowIndex]){
      const listInputByRowIndex = listDintMappingWidgetStartAt[rowIndex].getElementsByTagName("input");
      if(listInputByRowIndex.length == 2){
        const isChange = (listInputByRowIndex[0].value != tableMapping.startLine) || (listInputByRowIndex[1].value != tableMapping.startColumn);
        optionAffectMappingHandler.displayWarningMessage(tableMappingWarningDivID, isChange);
        if(isChange){
          return;
        }
      }
    }

    // compare list selected target table
    if(targetTableIdByRowIndex && tableMapping){
      const listTargetTableByRowIndex = JSON.parse(targetTableIdByRowIndex);
      const listTargetTable = [];
      if(!(listTargetTableByRowIndex && listTargetTableByRowIndex[rowIndex])){
        return;
      }
      listTargetTableByRowIndex[rowIndex].forEach(divId => {
        const selectBoxElement = document.getElementsByName(divId);
        if(selectBoxElement && selectBoxElement.length > 0){
          const selectedTable = document.getElementsByName(divId)[0].value;
          if(selectedTable && !selectedTable.startsWith('ebx:null')){
            listTargetTable.push(selectedTable);
          }
        }
      });
      const originTargetTablePath = tableMapping['targetTablePath'] ? tableMapping['targetTablePath']: tableMapping['targetTable'];
      const isChange = JSON.stringify(originTargetTablePath) != JSON.stringify(listTargetTable);
      optionAffectMappingHandler.displayWarningMessage(tableMappingWarningDivID, isChange);
    }
  }
}

