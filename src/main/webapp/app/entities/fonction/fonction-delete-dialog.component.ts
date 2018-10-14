import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Fonction } from './fonction.model';
import { FonctionPopupService } from './fonction-popup.service';
import { FonctionService } from './fonction.service';

@Component({
    selector: 'jhi-fonction-delete-dialog',
    templateUrl: './fonction-delete-dialog.component.html'
})
export class FonctionDeleteDialogComponent {

    fonction: Fonction;

    constructor(
        private fonctionService: FonctionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fonctionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fonctionListModification',
                content: 'Deleted an fonction'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-fonction-delete-popup',
    template: ''
})
export class FonctionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fonctionPopupService: FonctionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fonctionPopupService
                .open(FonctionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
