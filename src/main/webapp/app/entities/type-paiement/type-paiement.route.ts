import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypePaiementComponent } from './type-paiement.component';
import { TypePaiementDetailComponent } from './type-paiement-detail.component';
import { TypePaiementPopupComponent } from './type-paiement-dialog.component';
import { TypePaiementDeletePopupComponent } from './type-paiement-delete-dialog.component';

@Injectable()
export class TypePaiementResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const typePaiementRoute: Routes = [
    {
        path: 'type-paiement',
        component: TypePaiementComponent,
        resolve: {
            'pagingParams': TypePaiementResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typePaiement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-paiement/:id',
        component: TypePaiementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typePaiement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typePaiementPopupRoute: Routes = [
    {
        path: 'type-paiement-new',
        component: TypePaiementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typePaiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-paiement/:id/edit',
        component: TypePaiementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typePaiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-paiement/:id/delete',
        component: TypePaiementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typePaiement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
