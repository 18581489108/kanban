/**
 *
 * 定义使用的数据
 */

namespace kanban {
    /**
     * 定义Key - Path的数据
     */
    export class KeyPath {
        readonly key : string;
        readonly path : string;

        constructor(key : string, path : string) {
            this.key = key;
            this.path = path;
        }
    }

    /**
     * 定义live2d的数据类
     */
    export class Live2dData {
        moc : KeyPath;
        textures : Array<KeyPath>;
        physics : KeyPath;
        motions : Array<KeyPath>;
    }

    export let live2dData = new Live2dData();
    live2dData.moc = new KeyPath("moc", "./assets/hiyori/hiyori.moc3");

    live2dData.textures = [
        new KeyPath("texture", "./assets/hiyori/hiyori.2048/texture_00.png")
    ];

    live2dData.physics = new KeyPath("physics", "./assets/hiyori/hiyori.physics3.json");

    live2dData.motions = [
        new KeyPath("hiyori_m01", "./assets/hiyori/motions/hiyori_m01.motion3.json"),
        new KeyPath("hiyori_m02", "./assets/hiyori/motions/hiyori_m02.motion3.json"),
    ];

    /*
    let live2dData1 = {
        moc : {
            key : "moc",
            path : "./assets/hiyori/hiyori.moc3"
        },
        textures : [
            {
                key : "texture",
                path : "./assets/hiyori/hiyori.2048/texture_00.png"
            }
        ],
        physics : {
            key : "physics",
            path : "./assets/hiyori/hiyori.physics3.json"
        },
        motions : [
            {
                key : "hiyori_m01",
                path : "./assets/hiyori/motions/hiyori_m01.motion3.json"
            },
            {
                key : "hiyori_m02",
                path : "./assets/hiyori/motions/hiyori_m02.motion3.json"
            }
        ]
    };
    */
}
