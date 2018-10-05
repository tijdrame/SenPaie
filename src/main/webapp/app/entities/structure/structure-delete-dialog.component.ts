import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Structure } from './structure.model';
import { StructurePopupService } from './structure-popup.service';
import { StructureService } from './structure.service';

@Component({
    selector: 'jhi-structure-delete-dialog',
    templateUrl: './structure-delete-dialog.component.html'
})
export class StructureDeleteDialogComponent {

    structure: Structure;

    constructor(
        private structureService: StructureService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.structureService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'structureListModification',
                content: 'Deleted an structure'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-structure-delete-popup',
    template: ''
})
export class StructureDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private structurePopupService: StructurePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.structurePopupService
                .open(StructureDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
