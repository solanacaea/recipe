import RecipeProperty from './recipe-property'

const optimalStageOptions = ['第一周', '第二周', '第三周', '第四周', '第五周', '第六周'];
const optimalTimeOptions = ['早餐', '早点', '午餐', '点心', '晚餐', '夜宵'];
const efficacyOptions = ['补血', '活血', '清热', '祛湿'];
const propertyOptions = ['低糖', '低热量', '无特殊属性'];
const categoryOptions = ['主食', '汤', '菜', '饮品'];

const options = {
  optimalStageOptions,
  optimalTimeOptions,
  efficacyOptions,
  propertyOptions,
  categoryOptions
}

const RecipeProperties = [
  new RecipeProperty('category', '类别', categoryOptions),
  new RecipeProperty('optimalStage', '适宜阶段', optimalStageOptions),
  new RecipeProperty('optimalTime', '适宜时间', optimalTimeOptions),
  new RecipeProperty('property', '属性', propertyOptions),
  new RecipeProperty('efficacy', '功效', efficacyOptions)
]

export default {
  RecipeProperties,
  options
};