var kanban;
(function (kanban) {
    var KeyPath = (function () {
        function KeyPath(key, path) {
            this.key = key;
            this.path = path;
        }
        return KeyPath;
    }());
    kanban.KeyPath = KeyPath;
    var Live2dData = (function () {
        function Live2dData() {
        }
        return Live2dData;
    }());
    kanban.Live2dData = Live2dData;
    kanban.live2dData = new Live2dData();
    kanban.live2dData.moc = new KeyPath("moc", "./assets/hiyori/hiyori.moc3");
    kanban.live2dData.textures = [
        new KeyPath("texture", "./assets/hiyori/hiyori.2048/texture_00.png")
    ];
    kanban.live2dData.physics = new KeyPath("physics", "./assets/hiyori/hiyori.physics3.json");
    kanban.live2dData.motions = [
        new KeyPath("hiyori_m01", "./assets/hiyori/motions/hiyori_m01.motion3.json"),
        new KeyPath("hiyori_m02", "./assets/hiyori/motions/hiyori_m02.motion3.json"),
    ];
})(kanban || (kanban = {}));
