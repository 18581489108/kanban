/*
 * Copyright(c) Live2D Inc. All rights reserved.
 *
 * Use of this source code is governed by the Live2D Open Software license
 * that can be found at http://live2d.com/eula/live2d-open-software-license-agreement_en.html.
 */

import {Live2DCubismFramework as cubismjson} from "./utils/cubismjson";
import {Live2DCubismFramework as cubismidmanager} from "./id/cubismidmanager";
import {Live2DCubismFramework as cubismrenderer} from "./rendering/cubismrenderer";
import {CubismLogInfo, CubismLogWarning, CSM_ASSERT} from "./utils/cubismdebug";
import Value = cubismjson.Value;
import CubismIdManager = cubismidmanager.CubismIdManager;
import CubismRenderer = cubismrenderer.CubismRenderer;

export function strtod(s: string, endPtr: string[]): number
{
    let index: number = 0;
    for(let i: number = 1; ; i++)
    {
        let testC: string = s.slice(i - 1, i);

        // 指数・マイナスの可能性があるのでスキップする
        if(testC=='e' || testC=='-' || testC == 'E')
        {
            continue;
        }

　　　  // 文字列の範囲を広げていく
        let test: string = s.substring(0, i);
        let number: number = Number(test);
        if(isNaN(number))
        {
　　　　    // 数値として認識できなくなったので終了
            break;
        }

　　　 // 最後に数値としてできたindexを格納しておく
        index = i;
    }
    let d = parseFloat(s);  // パースした数値

    if(isNaN(d))
    {
　　　　 // 数値として認識できなくなったので終了
        d = NaN;
    }

    endPtr[0] = s.slice(index);　// 後続の文字列

    return d;
}

export namespace Live2DCubismFramework
{
    // ファイルスコープの変数を初期化
    
    let s_isStarted: boolean = false;
    let s_isInitialized: boolean = false;
    let s_option: Option = null;
    let s_cubismIdManager: CubismIdManager = null;
    
    /**
     * Framework内で使う定数の宣言
     */
    export namespace Constant
    {
        export const vertexOffset: number = 0;     // メッシュ頂点のオフセット値
        export const vertexStep: number = 2;       // メッシュ頂点のステップ値
    }

    export function csmDelete<T>(address: T): void
    {
        if(!address)
        {
            return;
        }

        address = void 0;
    }

    /**
     * Live2D Cubism3 Original Workflow SDKのエントリポイント
     * 利用開始時はCubismFramework.initialize()を呼び、CubismFramework.dispose()で終了する。
     */
    export class CubismFramework
    {
        /**
         * @brief    Cubism FrameworkのAPIを使用可能にする。<br>
         *            APIを実行する前に必ずこの関数を実行すること。<br>
         *            引数に必ずメモリアロケータを渡してください。<br>
         *            一度準備が完了して以降は、再び実行しても内部処理がスキップされます。
         *
         * @param    allocator   ICubismAllocatorクラスのインスタンス
         * @param    option      Optionクラスのインスタンス
         *
         * @return   準備処理が完了したらtrueが返ります。
         */
        public static startUp(option: Option = null): boolean
        {
            if(s_isStarted)
            {
                CubismLogInfo("CubismFramework::StartUp() is already done.");
                return s_isStarted;
            }

            s_option = option;

            if(s_option != null)
            {
                // TODO Core::csmSetLogFunction(s_option->LogFunction);
            }

            s_isStarted = true;

            // Live2D Cubism Coreバージョン情報を表示
            if(s_isStarted)
            {
                const version: number = 1; // TODO Core::csmGetVersion()
                const major: number = ((version & 0xFF000000) >> 24);
                const minor: number = ((version & 0x00FF0000) >> 16);
                const patch: number = ((version & 0x0000FFFF));
                const versionNumber: number = version;

                CubismLogInfo("Live2D Cubism Core version: %02d.%02d.%04d (%d)", major, minor, patch, versionNumber);
            }

            CubismLogInfo("CubismFramework::StartUp() is complete.");

            return s_isStarted;
        }

        /**
         * StartUp()で初期化したCubismFrameworkの各パラメータをクリアします。
         * Dispose()したCubismFrameworkを再利用する際に利用してください。
         */
        public static cleanUp(): void
        {
            s_isStarted = false;
            s_isInitialized = false;
            s_option = null;
            s_cubismIdManager = null;
        }

        /**
         * Cubism Framework内のリソースを初期化してモデルを表示可能な状態にします。<br>
         *     再度Initialize()するには先にDispose()を実行する必要があります。
         */
        public static initialize(): void
        {
            CSM_ASSERT(s_isStarted);
            if(!s_isStarted)
            {
                CubismLogWarning("CubismFramework is not started.");
                return;
            }

            // --- s_isInitializedによる連続初期化ガード ---
            // 連続してリソース確保が行われないようにする。
            // 再度Initialize()するには先にDispose()を実行する必要がある。
            if (s_isInitialized)
            {
                CubismLogWarning("CubismFramework::Initialize() skipped, already initialized.");
                return;
            }

            //---- static 初期化 ----
            Value.staticInitializeNotForClientCall();

            s_cubismIdManager = new CubismIdManager();

            s_isInitialized = true;

            CubismLogInfo("CubismFramework::Initialize() is complete.");
        }

        /**
         * Cubism Framework内の全てのリソースを解放します。
         *      ただし、外部で確保されたリソースについては解放しません。
         *      外部で適切に破棄する必要があります。
         */
        public static dispose(): void
        {
            CSM_ASSERT(s_isStarted);
            if(!s_isStarted)
            {
                CubismLogWarning("CubismFramework is not started.");
                return;
            }

            // --- s_isInitializedによる未初期化解放ガード ---
            // dispose()するには先にinitialize()を実行する必要がある。
            if(!s_isInitialized)    // false...リソース未確保の場合
            {
                CubismLogWarning("CubismFramework::Dispose() skipped, not initialized.");
                return;
            }

            Value.staticReleaseNotForClientCall();

            s_cubismIdManager.release();
            s_cubismIdManager = void 0;

            // レンダラの静的リソース（シェーダプログラム他）を解放する
            CubismRenderer.StaticRelease();

            s_isInitialized = false;

            CubismLogInfo("CubismFramework::Dispose() is complete.");
        }

        /**
         * 現在のログ出力レベル設定の値を返す。
         *
         * @return  現在のログ出力レベル設定の値
         */
        public static getLoggingLevel(): Option.LogLevel
        {
            if (s_option != null)
            {
                return s_option.loggingLevel;
            }
            return Option.LogLevel.LogLevel_Off;
        }

        /**
         * Cubism FrameworkのAPIを使用する準備が完了したかどうか
         * @return APIを使用する準備が完了していればtrueが返ります。
         */
        public static isStarted(): boolean
        {
            return s_isStarted;
        }

        /**
         * Cubism Frameworkのリソース初期化がすでに行われているかどうか
         * @return リソース確保が完了していればtrueが返ります
         */
        public static isInitialized(): boolean
        {
            return s_isInitialized;
        }

        /**
         * IDマネージャのインスタンスを取得する
         * @return CubismManagerクラスのインスタンス
         */
        public static getIdManager(): CubismIdManager
        {
            return s_cubismIdManager;
        }

        /**
         * 静的クラスとして使用する
         * インスタンス化させない
         */
        private constructor()
        {

        }
    }
}

export class Option
{
    loggingLevel: Option.LogLevel;  // ログ出力レベルの設定
}

/**
 * ログ出力のレベル
 */
export namespace Option
{
    export enum LogLevel
    {
        LogLevel_Verbose = 0,   // 詳細ログ
        LogLevel_Debug,         // デバッグログ
        LogLevel_Info,          // Infoログ
        LogLevel_Warning,       // 警告ログ
        LogLevel_Error,         // エラーログ
        LogLevel_Off            // ログ出力無効
    }
}
