import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { MoisConcerne } from './mois-concerne.model';
import { MoisConcerneService } from './mois-concerne.service';

@Injectable()
export class MoisConcernePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private moisConcerneService: MoisConcerneService

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
                this.moisConcerneService.find(id)
                    .subscribe((moisConcerneResponse: HttpResponse<MoisConcerne>) => {
                        const moisConcerne: MoisConcerne = moisConcerneResponse.body;
                        this.ngbModalRef = this.moisConcerneModalRef(component, moisConcerne);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.moisConcerneModalRef(component, new MoisConcerne());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    moisConcerneModalRef(component: Component, moisConcerne: MoisConcerne): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.moisConcerne = moisConcerne;
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
