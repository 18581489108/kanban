import path from 'path';
import HtmlWebpackPlugin from 'html-webpack-plugin';
import { TsConfigPathsPlugin } from 'awesome-typescript-loader';
const CopyWebpackPlugin = require('copy-webpack-plugin');

const { NODE_ENV } = process.env;
const OUTPUT_PATH = path.resolve(__dirname, 'dist');

const config = {
  context: path.resolve(__dirname, './src'),
  mode: NODE_ENV,
  entry: './index.tsx',
  devtool: NODE_ENV === 'production' ? 'source-map' : 'cheap-module-source-map',
  output: {
    filename: '[name].bundle.js',
    path: OUTPUT_PATH,
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: [
          {
            loader: 'babel-loader',
            options: {
              babelrc: false,
              plugins: ['react-hot-loader/babel'], // 这个loader导致了打包后的代码出现了本地路径，后期需要进行替换
            },
          },
          'awesome-typescript-loader',
        ],
      },
      { enforce: 'pre', test: /\.js$/, loader: 'source-map-loader' },
      {
        test: /\.render\.js$/,
        use: ['file-loader'],
      },
      { test: /\.css$/, use: ['style-loader', 'css-loader'] },
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js', '.json'],
    plugins: [new TsConfigPathsPlugin()],
  },
  plugins: [
    new HtmlWebpackPlugin({
      inject: true,
      template: path.resolve('./src/public/index.html'),
      chunks: ['main'],
    }),
    new CopyWebpackPlugin([
        {
            from: "./assets/live2d/**/*",
            to: OUTPUT_PATH
        },
        {
          from: "./assets/js/**/*",
            to: OUTPUT_PATH
        }
    ])
  ],
  devServer: {
    hot: true,
    inline: true,
    //contentBase: OUTPUT_PATH,
    compress: true,
    port: 3000,
  },
}

export default config;