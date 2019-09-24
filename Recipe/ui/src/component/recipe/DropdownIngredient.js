
import React, { Component } from 'react';
import { Radio, Input } from 'antd';

const plainOptions = ['素菜', '蛋奶', '猪肉类', '其它肉类'];

class DropDownComponent extends Component {
  componentDidMount() {
    this.props.onRef(this);
  }

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
    if (e == null) 
      return;
    // console.log('radio checked', e.target.value);
    this.setState({
      value: e.target.value,
    });
  };

  getValue() {
    return this.state.value;
  }
  
  render() {
    return (
      <div>
        <div style={{ borderBottom: '1px solid #E9E9E9' }}>
        </div>
          <Radio.Group onChange={this.onChange} defaultValue="素菜"
            options={plainOptions} value={this.state.value}>
          </Radio.Group>
      </div>
    );
  }
}

export default DropDownComponent;