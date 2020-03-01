import {
    Table, Input, Button, Icon, Popconfirm, message, Tag, Tooltip
} from 'antd';
import Highlighter from 'react-highlight-words';
import React, { Component } from 'react';
import GeneratorDrawer from './GeneratorDrawer';
import axios from 'axios';
import moment from 'moment';
import * as GenerateService from '../../service/GenerateService';

const ButtonGroup = Button.Group;
const confirmText = '确定要删除吗?';

class GenerateMain extends Component {
    componentDidMount() {
        this.refresh();
    }

    state = {
        searchText: '',
        filteredInfo: null,
        sortedInfo: null,
        selectedRow: null,
        data: null,
        loading: false,
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
            title: '客户',
            dataIndex: 'userName',
            key: 'userName',
            ...this.getColumnSearchProps('userName'),
            width: 120,
            // }, {
            //   title: '食谱类别',
            //   dataIndex: 'type',
            //   key: 'type',
            //   ...this.getColumnSearchProps('type'),
            //   width: 120,
        }, {
            title: '备注',
            dataIndex: 'memo',
            key: 'memo',
            ...this.getColumnSearchProps('memo'),
            // }, {
            //   title: '食谱特点',
            //   dataIndex: 'feature',
            //   key: 'feature',
            //   ...this.getColumnSearchProps('feature'),
            //   //textWrap: 'word-break', ellipsis: true,
            //   render: largeText,
        }, {
            title: '饮食建议',
            dataIndex: 'suggestion',
            key: 'suggestion',
            ...this.getColumnSearchProps('suggestion'),
            //textWrap: 'word-break', ellipsis: true,
            render: largeText,
            // }, {
            //   title: '注意',
            //   dataIndex: 'note',
            //   key: 'note',
            //   ...this.getColumnSearchProps('note'),
            //   render: largeText,
            // }, {
            //   title: '声明',
            //   dataIndex: 'declare',
            //   key: 'declare',
            //   ...this.getColumnSearchProps('declare'),
            /*
            render: (text, record) => (
              <div style={{ wordWrap: 'break-word', wordBreak: 'break-word', 
                  display: 'WebkitBox', WebkitLineClamp: 2, WebkitBoxOrient: "vertical",
                  overflow: 'hidden', textOverflow: 'ellipsis' }}>
                {text}
              </div>
            ),
            */
            // render: largeText,
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
            title: '生成日期',
            dataIndex: 'createdDate',
            key: 'createdDate',
            ...this.getColumnSearchProps('createdDate'),
            render: formatterTime,
            width: 150,
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
                <Button block icon="plus" width="40px" onClick={this.openPanel.bind(this, null)}>生成食谱</Button>
            </ButtonGroup>

            <Table rowKey={record => record.id} columns={columns} dataSource={this.state.data}
                bordered onChange={this.handleChange}
                pagination={{ pageSize: 50 }} />
            <GeneratorDrawer onRef={this.onRef} refresh={this.refresh} />
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
       GenerateService.deleteGenerator(record)
            .then((res) => {
                this.refresh()
            }).catch((err) => {
                message.info('Error [' + err.message + ']...');
                console.log(err)
            })
    }

    refresh = () => {
        GenerateService.getAll()
            .then((res) => {
                //console.log(res);
                this.setState({ data: res });
                //this.handleToggle('loading')
            })
            .catch((err) => {
                message.info('Error [' + err + ']...');
                console.log(err)
            })
    }

    // handleToggle = prop => enable => {
    //     this.setState({ [prop]: enable });
    // };

}

const formatterTime = (val) => {
    return val ? moment(val).format('YYYY-MM-DD HH:mm') : ''
}

const largeText = (val) => {
    var tip = val;
    if (val && val.length > 15)
        val = val.slice(0, 15) + '...';

    return <Tooltip title={tip}>
        <span>{val}</span>
    </Tooltip>
}

export default GenerateMain;
