import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Motif } from './motif.model';
import { MotifPopupService } from './motif-popup.service';
import { MotifService } from './motif.service';

@Component({
    selector: 'jhi-motif-delete-dialog',
    templateUrl: './motif-delete-dialog.component.html'
})
export class MotifDeleteDialogComponent {

    motif: Motif;

    constructor(
        private motifService: MotifService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.motifService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'motifListModification',
                content: 'Deleted an motif'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-motif-delete-popup',
    template: ''
})
export class MotifDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private motifPopupService: MotifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.motifPopupService
                .open(MotifDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
