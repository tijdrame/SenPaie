import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { AvantageCollab } from './avantage-collab.model';
import { AvantageCollabService } from './avantage-collab.service';

@Injectable()
export class AvantageCollabPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private avantageCollabService: AvantageCollabService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.avantageCollabService.find(id)
                    .subscribe((avantageCollabResponse: HttpResponse<AvantageCollab>) => {
                        const avantageCollab: AvantageCollab = avantageCollabResponse.body;
                        this.ngbModalRef = this.avantageCollabModalRef(component, avantageCollab);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.avantageCollabModalRef(component, new AvantageCollab());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    avantageCollabModalRef(component: Component, avantageCollab: AvantageCollab): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.avantageCollab = avantageCollab;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
