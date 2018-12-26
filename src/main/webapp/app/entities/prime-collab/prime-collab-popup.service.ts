import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { PrimeCollab } from './prime-collab.model';
import { PrimeCollabService } from './prime-collab.service';

@Injectable()
export class PrimeCollabPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private primeCollabService: PrimeCollabService

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
                this.primeCollabService.find(id)
                    .subscribe((primeCollabResponse: HttpResponse<PrimeCollab>) => {
                        const primeCollab: PrimeCollab = primeCollabResponse.body;
                        this.ngbModalRef = this.primeCollabModalRef(component, primeCollab);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.primeCollabModalRef(component, new PrimeCollab());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    primeCollabModalRef(component: Component, primeCollab: PrimeCollab): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.primeCollab = primeCollab;
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
