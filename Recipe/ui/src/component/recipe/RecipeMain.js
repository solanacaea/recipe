import {
    Table, Input, Button, Icon, Popconfirm, message, Tag
} from 'antd';
import Highlighter from 'react-highlight-words';
import React, { Component } from 'react';
import RecipeDrawer from './RecipeDrawer'
import * as DishService from '../../service/DishService';

const ButtonGroup = Button.Group;
const confirmText = '确定要删除吗?';
//const data = [];

class RecipeMain extends Component {
    componentDidMount() {
        this.refresh();
    }

    state = {
        searchText: '',
        filteredInfo: null,
        sortedInfo: null,
        selectedRow: null,
        data: null,
    };

    getColumnSearchProps = (dataIndex) => ({
        filterDropdown: ({
            setSelectedKeys, selectedKeys, confirm, clearFilters,
        }) => (
                <div style={{ padding: 8 }}>
                    <Input
                        ref={node => { this.searchInput = node; }}
                        placeholder={`Search ${dataIndex}`}
                        value={selectedKeys[0]}
                        onChange={e => setSelectedKeys(e.target.value ? [e.target.value] : [])}
                        onPressEnter={() => this.handleSearch(selectedKeys, confirm)}
                        style={{ width: 188, marginBottom: 8, display: 'block' }}
                    />
                    <Button
                        type="primary"
                        onClick={() => this.handleSearch(selectedKeys, confirm)}
                        icon="search"
                        size="small"
                        style={{ width: 90, marginRight: 8 }}
                    >
                        Search
          </Button>
                    <Button
                        onClick={() => this.handleReset(clearFilters)}
                        size="small"
                        style={{ width: 90 }}
                    >
                        Reset
          </Button>
                </div>
            ),
        filterIcon: filtered => <Icon type="search" style={{ color: filtered ? '#1890ff' : undefined }} />,
        onFilter: (value, record) => record[dataIndex].toString().toLowerCase().includes(value.toLowerCase()),
        onFilterDropdownVisibleChange: (visible) => {
            if (visible) {
                setTimeout(() => this.searchInput.select());
            }
        },
        render: (text) => (
            <Highlighter
                highlightStyle={{ backgroundColor: '#ffc069', padding: 0 }}
                searchWords={[this.state.searchText]}
                autoEscape
                textToHighlight={text == null ? "" : text.toString()}
            />
        ),
    })

    handleSearch = (selectedKeys, confirm) => {
        confirm();
        this.setState({ searchText: selectedKeys[0] });
    }

    handleReset = (clearFilters) => {
        clearFilters();
        this.setState({ searchText: '' });
    }

    handleChange = (pagination, filters, sorter) => {
        //console.log('Various parameters', pagination, filters, sorter);
        this.setState({
            filteredInfo: filters,
            sortedInfo: sorter,
        });
    }

    clearFilters = () => {
        this.setState({ filteredInfo: null });
    }

    clearAll = () => {
        this.setState({
            filteredInfo: null,
            sortedInfo: null,
            selectedRow: null,
        });
    }

    render() {
        let { sortedInfo, filteredInfo } = this.state;
        sortedInfo = sortedInfo || {};
        filteredInfo = filteredInfo || {};

        const columns = [{
            title: '食谱名',
            dataIndex: 'name',
            key: 'name',
            ...this.getColumnSearchProps('name'),
            //grey, brown, navy, tan, coral, red, salmon, plum, crimson, olive, violet, purple
            render: item => (
                <span>
                    {item && item.split(',').map(tag => {
                        var color = 'tan';
                        return (
                            <Tag color={color} key={tag}>
                                {tag.toUpperCase()}
                            </Tag>
                        );
                    })}
                </span>
            ),
        }, {
            title: '食谱',
            dataIndex: 'content',
            key: 'content',
            ...this.getColumnSearchProps('content'),
            render: item => (
                <span>
                    {item.split(',').map(tag => {
                        var color = 'pink';
                        return (
                            <Tag color={color} key={tag}>
                                {tag.toUpperCase()}
                            </Tag>
                        );
                    })}
                </span>
            ),
        }, {
            title: '类别',
            dataIndex: 'category',
            key: 'category',
            filters: [
                { text: '主食', value: '主食' },
                { text: '汤', value: '汤' },
                { text: '菜', value: '菜' },
                { text: '饮品', value: '饮品' },
            ],
            filteredValue: filteredInfo.category || null,
            onFilter: (value, record) => record.category.includes(value),
            sorter: (a, b) => a.category.length - b.category.length,
            sortOrder: sortedInfo.columnKey === 'category' && sortedInfo.order,
            render: item => (
                <span>
                    {item.split(',').map(tag => {
                        var color = 'orange';
                        return (
                            <Tag color={color} key={tag}>
                                {tag.toUpperCase()}
                            </Tag>
                        );
                    })}
                </span>
            ),
        }, {
            title: '适宜阶段',
            dataIndex: 'optimalStage',
            key: 'optimalStage',
            filters: [
                { text: '第一周', value: '第一周' },
                { text: '第二周', value: '第二周' },
                { text: '第三周', value: '第三周' },
                { text: '第四周', value: '第四周' },
                { text: '第五周', value: '第五周' },
                { text: '第六周', value: '第六周' },
            ],
            filteredValue: filteredInfo.optimalStage || null,
            onFilter: (value, record) => record.optimalStage.includes(value),
            sorter: (a, b) => a.optimalStage.length - b.optimalStage.length,
            sortOrder: sortedInfo.columnKey === 'optimalStage' && sortedInfo.order,
            render: item => (
                <span>
                    {item.split(',').map(tag => {
                        var color = 'blue';
                        return (
                            <Tag color={color} key={tag}>
                                {tag.toUpperCase()}
                            </Tag>
                        );
                    })}
                </span>
            ),
        }, {
            title: '适宜时间',
            dataIndex: 'optimalTime',
            key: 'optimalTime',
            filters: [
                { text: '早餐', value: '早餐' },
                { text: '早点', value: '早点' },
                { text: '午餐', value: '午餐' },
                { text: '点心', value: '点心' },
                { text: '晚餐', value: '晚餐' },
                { text: '夜宵', value: '夜宵' },
            ],
            filteredValue: filteredInfo.optimalTime || null,
            onFilter: (value, record) => record.optimalTime.includes(value),
            sorter: (a, b) => a.optimalTime.length - b.optimalTime.length,
            sortOrder: sortedInfo.columnKey === 'optimalTime' && sortedInfo.order,
            render: item => (
                <span>
                    {item.split(',').map(tag => {
                        var color = 'green';
                        return (
                            <Tag color={color} key={tag}>
                                {tag.toUpperCase()}
                            </Tag>
                        );
                    })}
                </span>
            ),
        }, {
            title: '原料',
            dataIndex: 'ingredient',
            key: 'ingredient',
            filters: [
                { text: '素菜', value: '素菜' },
                { text: '蛋奶', value: '蛋奶' },
                { text: '猪肉类', value: '猪肉类' },
                { text: '其它肉类', value: '其它肉类' },
            ],
            filteredValue: filteredInfo.ingredient || null,
            onFilter: (value, record) => record.ingredient.includes(value),
            sorter: (a, b) => a.ingredient.length - b.ingredient.length,
            sortOrder: sortedInfo.columnKey === 'ingredient' && sortedInfo.order,
            render: item => (
                <span>
                    {item && item.split(',').map(tag => {
                        var color = 'navy';
                        return (
                            <Tag color={color} key={tag}>
                                {tag}
                            </Tag>
                        );
                    })}
                </span>
            ),
        }, {
            title: '属性',
            dataIndex: 'property',
            key: 'property',
            filters: [
                { text: '低糖', value: '低糖' },
                { text: '低热量', value: '低热量' },
                { text: '无特殊属性', value: '无特殊属性' },
            ],
            filteredValue: filteredInfo.property || null,
            onFilter: (value, record) => record.property.includes(value),
            sorter: (a, b) => a.property.length - b.property.length,
            sortOrder: sortedInfo.columnKey === 'property' && sortedInfo.order,
            render: item => (
                <span>
                    {item.split(',').map(tag => {
                        var color = 'purple';
                        return (
                            <Tag color={color} key={tag}>
                                {tag.toUpperCase()}
                            </Tag>
                        );
                    })}
                </span>
            ),
        }, {
            title: '体质',
            dataIndex: 'efficacy',
            key: 'efficacy',
            filters: [
                { text: '气虚', value: '气虚' },
                { text: '血瘀', value: '血瘀' },
                { text: '阴虚', value: '阴虚' },
                { text: '痰湿', value: '痰湿' },
            ],
            filteredValue: filteredInfo.efficacy || null,
            onFilter: (value, record) => record.efficacy.includes(value),
            sorter: (a, b) => a.efficacy.length - b.efficacy.length,
            sortOrder: sortedInfo.columnKey === 'efficacy' && sortedInfo.order,
            render: efficacy => (
                <span>
                    {efficacy.split(',').map(tag => {
                        var color;
                        if (tag === '痰湿')
                            color = 'geekblue';
                        else if (tag === '阴虚')
                            color = 'green';
                        else if (tag === '气虚')
                            color = 'blue';
                        else if (tag === '血瘀')
                            color = 'volcano';

                        return (
                            <Tag color={color} key={tag}>
                                {tag.toUpperCase()}
                            </Tag>
                        );
                    })}
                </span>
            ),
        }, {
            title: '操作',
            key: 'action',
            render: (text, record) => (
                <span>
                    <ButtonGroup size="small">
                        <Button onClick={this.openPanel.bind(this, record)} >
                            <Icon type="edit" theme="filled" />
                        </Button>

                        <Popconfirm placement="topRight" title={confirmText} onConfirm={this.delete.bind(this, record)} okText="Yes" cancelText="No">
                            <Button>
                                <Icon type="delete" theme="filled" />
                            </Button>
                        </Popconfirm>
                    </ButtonGroup>
                </span>
            ),
        }];
        return <div>
            <ButtonGroup>
                <Button block icon="plus" width="40px" onClick={this.openPanel.bind(this, null)}>新增</Button>
            </ButtonGroup>
            <Table rowKey={record => record.id} columns={columns} dataSource={this.state.data} onChange={this.handleChange}
                bordered size="small" pagination={{ pageSize: 20 }} />
            <RecipeDrawer onRef={this.onRef} refresh={this.refresh} />
        </div>;
    }

    onRef = (ref) => {
        this.drawer = ref
    }

    openPanel = (record) => {
        this.drawer.showDrawer(record);
    }

    delete = (record) => {
        message.info('Deleting [' + record.id + ']...');
        DishService.deleteDish(record)
            .then((res) => {
                this.refresh()
            }).catch((err) => {
                message.info('Error [' + err + ']...');
                console.log(err)
            })
    }

    refresh = () => {
        DishService.getAllDishes()
            .then((res) => {
                this.setState({ data: res });
            })
            .catch((err) => {
                message.info('Error [' + err + ']...');
            })
    }

}

export default RecipeMain