import React, { Component } from 'react';
import { Checkbox } from 'antd';

const CheckboxGroup = Checkbox.Group;

class PropertyDropdownComponent extends Component {

  constructor(props) {
    super(props);
    this.state = {
      checkedList: this.props.checkedList,
      indeterminate: false,
      checkAll: false
    };
  }

  onChange = (checkedItems) => {
    const { plainOptions } = this.props;
    const checkedListLen = checkedItems.length;
    const plainOptionsLen = plainOptions.length;
    const newState = {
      checkedList: checkedItems,
      indeterminate: checkedListLen > 0 && checkedListLen < plainOptionsLen,
      checkAll: checkedListLen === plainOptionsLen
    };
    this.setState(newState, () => {
      this.triggerValueChanged();
    });
  }

  onCheckAllChange = (e) => {
    const { plainOptions } = this.props;
    const newState = {
      checkedList: e.target.checked ? plainOptions : [],
      indeterminate: false,
      checkAll: e.target.checked
    };
    this.setState(newState, () => {
      this.triggerValueChanged();
    });
  }

  triggerValueChanged() {
    const { checkedList } = this.state;
    const { propertyName, valueChanged } = this.props;
    if (valueChanged) {
      valueChanged(checkedList, propertyName);
    }
  }

  render() {
    const { plainOptions }= this.props;
    const { indeterminate, checkAll, checkedList } = this.state;
    return (
      <div>
        <div style={{ borderBottom: '1px solid #E9E9E9' }}>
          <Checkbox
            indeterminate={indeterminate}
            onChange={this.onCheckAllChange}
            checked={checkAll}
          >
            全选
          </Checkbox>
        </div>
        <br />
        <CheckboxGroup options={plainOptions} value={checkedList} onChange={this.onChange} />
      </div>
    );
  }
}

export default PropertyDropdownComponent;