import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pieces } from './pieces.model';
import { PiecesPopupService } from './pieces-popup.service';
import { PiecesService } from './pieces.service';

@Component({
    selector: 'jhi-pieces-delete-dialog',
    templateUrl: './pieces-delete-dialog.component.html'
})
export class PiecesDeleteDialogComponent {

    pieces: Pieces;

    constructor(
        private piecesService: PiecesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.piecesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'piecesListModification',
                content: 'Deleted an pieces'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pieces-delete-popup',
    template: ''
})
export class PiecesDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private piecesPopupService: PiecesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.piecesPopupService
                .open(PiecesDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
