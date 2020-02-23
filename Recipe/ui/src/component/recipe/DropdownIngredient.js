
import React, { Component } from 'react';
import { Checkbox } from 'antd';

const CheckboxGroup = Checkbox.Group;
const plainOptions = ['素菜', '蛋奶', '猪肉类', '其它肉类'];
const defaultCheckedList = [];

class DropDownComponent extends Component {
    componentDidMount() {
        this.props.onRef(this);
    }

    state = {
        checkedList: defaultCheckedList,
        indeterminate: true,
        checkAll: false,
    };

    onChange = (checkedList) => {
        this.setState({
            checkedList,
            indeterminate: !!checkedList.length && (checkedList.length < plainOptions.length),
            checkAll: checkedList.length === plainOptions.length,
        });
    }

    onCheckAllChange = (e) => {
        this.setState({
            checkedList: e.target.checked ? plainOptions : [],
            indeterminate: false,
            checkAll: e.target.checked,
        });
    }

    getValue() {
        return this.state.checkedList;
    }

    /*
    state = {
      value: '素菜',
    };
  
    init = (value) => {
      // console.log(value)
      this.setState({
        value,
      });
    }
  
    onChange = e => {
      if (e == null || e.target == null) 
        return;
      // console.log('radio checked', e.target.value);
      this.setState({
        value: e.target.value,
      });
    };
  
    getValue() {
      return this.state.value;
    }
    */
    render() {
        return (
            <div>
                <div style={{ borderBottom: '1px solid #E9E9E9' }}>
                    <Checkbox
                        indeterminate={this.state.indeterminate}
                        onChange={this.onCheckAllChange}
                        checked={this.state.checkAll}
                    >
                        全选
                    </Checkbox>
                </div>
                <br />
                <CheckboxGroup options={plainOptions} value={this.state.checkedList} onChange={this.onChange} />
            </div>
            /*
            <div>
              <div style={{ borderBottom: '1px solid #E9E9E9' }}>
                <br/>
              </div>
              <br />
                <Radio.Group onChange={this.onChange} defaultValue="素菜"
                  options={plainOptions} value={this.state.value}>
                </Radio.Group>
            </div>
            */
        );
    }
}

export default DropDownComponent;