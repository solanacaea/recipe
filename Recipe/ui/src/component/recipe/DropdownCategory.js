
import React, { Component } from 'react';
import { Checkbox } from 'antd';

const CheckboxGroup = Checkbox.Group;
const plainOptions = ['主食', '汤', '菜', '饮品'];

class DropDownComponent extends Component {
    componentDidMount() {
        this.props.onRef(this);
    }

    state = {
        checkedList: [],
        indeterminate: true,
        checkAll: false,
    };

    onChange = (checkedList) => {
        this.setState({
            checkedList: checkedList,
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
        );
    }
}

export default DropDownComponent;