
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
    const radioStyle = {
      display: 'block',
      height: '30px',
      lineHeight: '30px',
    };
    return (
      <div>
        <div style={{ borderBottom: '1px solid #E9E9E9' }}>
        </div>
          <Radio.Group onChange={this.onChange} defaultValue="素菜"
            options={plainOptions} value={this.state.value}>
              <Radio style={radioStyle} value={4}>
                More...
                {this.state.value === 4 ? <Input style={{ width: 100, marginLeft: 10 }} /> : null}
              </Radio>
          </Radio.Group>
      </div>
    );
  }
}

export default DropDownComponent;