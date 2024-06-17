function domReady(fn) {
    document.addEventListener("DOMContentLoaded", fn);
    if (document.readyState === "interactive" || document.readyState === "complete") {
        fn();
    }
}

const simulation = {
    bodyNav: document.getElementById('dint-simulation-step__tableHeader-navBody'),

    loadPage() {
        document.addEventListener("DOMContentLoaded", function () {
            var tableContentitem = document.querySelector(".dint-simulation-step__tableContent");
            tableContentitem.style.display = 'block';

            let tableHeaderItemCurrent = document.getElementById('dint-simulation-step__tableHeader-item-0');
            let tableContentItemCurrent = document.getElementById("dint-simulation-step__tableContent-0");
            if (tableHeaderItemCurrent && tableContentItemCurrent) {
                simulation.displayTableItem(0);
            }

        });
    },
    countItems() {
        return document.querySelector(".dint-simulation-step__tableHeader-list").getElementsByTagName("li").length;
    },
    hiddenAllTableItems() {
        const countItems = this.countItems();
        let tableHeaderitem = document.querySelector(".dint-simulation-step__tableHeader-list").getElementsByTagName("li");
        for (let i = 0; i < countItems; i++) {
            tableHeaderitem[i].classList.remove("dint-simulation-step__tableHeader-item--active");

            let tableContentitem = document.getElementById("dint-simulation-step__tableContent-" + i);
            tableContentitem.style.display = 'none';
        }
    },
    hiddenTableItem(index) {
        let tableHeaderItemCurrent = document.getElementById('dint-simulation-step__tableHeader-item-' + index);
        tableHeaderItemCurrent.classList.remove("dint-simulation-step__tableHeader-item--active");

        let tableContentitem = document.getElementById("dint-simulation-step__tableContent-" + index);
        tableContentitem.style.display = 'none';
    },
    displayTableItem(index) {
        let tableHeaderItemCurrent = document.getElementById('dint-simulation-step__tableHeader-item-' + index);
        tableHeaderItemCurrent.classList.add("dint-simulation-step__tableHeader-item--active");

        let tableContentitem = document.getElementById("dint-simulation-step__tableContent-" + index);
        tableContentitem.style.display = 'block';
    },
    getActiveItem() {
        return document.getElementsByClassName("dint-simulation-step__tableHeader-item--active");
    },
    getIndexActiveItem() {
        let item = this.getActiveItem();
        return Number((item[0].id).split("-").pop());
    },
    prev() {
        let index = this.getIndexActiveItem();
        const length = this.countItems();

        this.hiddenTableItem(index);

        if (index == 0) {
            this.displayTableItem(length - 1);
            this.bodyNav.scrollLeft = this.bodyNav.scrollWidth - this.bodyNav.clientWidth;
        } else {
            this.displayTableItem(index - 1);

            let widthItemIndex = 0;
            for (i = index; i < length; i++) {
                widthItemIndex += document.getElementById('dint-simulation-step__tableHeader-item-' + i).offsetWidth;
            }

            if (this.bodyNav.clientWidth <= widthItemIndex) {
                this.bodyNav.scrollLeft = this.bodyNav.scrollWidth - this.bodyNav.clientWidth - widthItemIndex;
            }
        }

    },
    next() {
        let index = this.getIndexActiveItem();
        const length = this.countItems();
        this.hiddenTableItem(index);

        if (index == length - 1) {
            this.displayTableItem(0);
            this.bodyNav.scrollLeft = 0;
        } else {
            this.displayTableItem(index + 1);

            let widthItemIndex = 0;
            for (i = 0; i <= index; i++) {
                widthItemIndex += document.getElementById('dint-simulation-step__tableHeader-item-' + i).offsetWidth;
            }
            if (this.bodyNav.clientWidth <= widthItemIndex) {
                this.bodyNav.scrollLeft = widthItemIndex;
            }
        }
    },
    setActiveCurrent(index) {
        this.hiddenAllTableItems();
        this.displayTableItem(index);
    }
}

simulation.loadPage();

function clickHiddenProcessButton() {
    document.getElementById('hiddenProcessButton_dint').click();
}

function process() {
    clickHiddenProcessButton();
}

function showTemplateResult(data) {
    showInformationPopup(data.message);
}