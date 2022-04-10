// const addTodoPopUpConfig = {
//     openInitiatorSelector: '#header__add-todo_btn',
//     openInitiatorSelectorList: ['#header__add-todo_btn-1', '.header__add-todo_btn-2', 'etc'],
//     cancelInitiatorSelector: '.add-todo__footer-btn_cancel',
//     cancelByClickOutside: true,
//     classesManaging: [
//         {
//             elementSelector: '.add-todo__wrapper',
//             classListForToggling: ['none'],
//             TODO: classListForOnlyAdding: ['class-1', 'class-2'],
//             TODO: classListForOnlyRemoving: ['class-3', 'class-4'],
//         },
//         {
//             elementSelector: 'body',
//             classListForToggling: ['noscrool']
//         }
//     ]
// };
class PopUp {
    constructor(config) {
        this.config = config;

        this.openingInitialization();
    }

    manageClasses(open = false) {
        this.config.classesManaging.forEach(obj => {
            const elementsList = document.querySelectorAll(obj.elementSelector);
            for (let i = 0; i < elementsList.length; i++) {
                obj.classListForToggling.forEach(Class => {
                    if (open === true) {
                        if (elementsList[i].classList.contains(Class)) {
                            elementsList[i].classList.remove(Class);
                        }
                    } else {
                        if (!(elementsList[i].classList.contains(Class))) {
                            elementsList[i].classList.add(Class);
                        }
                    }
                });
            }
        });
    }

    closingInitialization() {
        document.querySelector(this.config.cancelInitiatorSelector).addEventListener('click', () => {
            this.manageClasses();
        });

        document.querySelector(this.config.popUpWrapperSelector).addEventListener('click', ev => {
            if (ev.target.classList.contains(this.config.popUpWrapperSelector.slice(1))) {
                this.manageClasses();
            }
        });
        // if (this.config.cancelByClickOutside) {
        //     const outsideBlock = document.querySelector(this.config.cancelByClickOutsideBlockSelector);
        //     document.addEventListener( 'click', (e) => {
        //         const withinBoundaries = e.composedPath().includes(outsideBlock);
             
        //         if ( ! withinBoundaries ) {
        //             this.manageClasses();
        //         }
        //     })
        // }
    }

    openingInitialization() {
        if (this.config.openInitiatorSelectorList) {
            this.config.openInitiatorSelectorList.forEach(selector => {
                const openInitiatorsList = document.querySelectorAll(selector);
                if (openInitiatorsList.length > 1) {
                    for (let i = 0; i < openInitiatorsList.length; i++) {
                        openInitiatorsList[i].addEventListener('click', () => {
                            this.manageClasses(true);
                            this.closingInitialization();
                        });
                    }
                } else if (openInitiatorsList.length === 1) {
                    openInitiatorsList[0].addEventListener('click', () => {
                        this.manageClasses(true);
                        this.closingInitialization();
                    });
                } else {
                    return;
                }
            });
        } else if (this.config.openInitiatorSelector) {
            const openInitiator = document.querySelectorAll(this.config.openInitiatorSelector);
            if (openInitiator.length > 1) {
                for (let i = 0; i < openInitiator.length; i++) {
                    openInitiator[i].addEventListener('click', () => {
                        this.manageClasses(true);
                        this.closingInitialization();
                    });
                }
            } else if (openInitiator.length === 1) {
                openInitiator[0].addEventListener('click', () => {
                    this.manageClasses(true);
                    this.closingInitialization();
                });
            } else {
                return;
            }
        } else {
            return;
        }
    }
}

export function createPopUp(config) {
    return new PopUp(config);
}