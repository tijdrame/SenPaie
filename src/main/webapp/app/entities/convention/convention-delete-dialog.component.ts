import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Convention } from './convention.model';
import { ConventionPopupService } from './convention-popup.service';
import { ConventionService } from './convention.service';

@Component({
    selector: 'jhi-convention-delete-dialog',
    templateUrl: './convention-delete-dialog.component.html'
})
export class ConventionDeleteDialogComponent {

    convention: Convention;

    constructor(
        private conventionService: ConventionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.conventionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'conventionListModification',
                content: 'Deleted an convention'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-convention-delete-popup',
    template: ''
})
export class ConventionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private conventionPopupService: ConventionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.conventionPopupService
                .open(ConventionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
